package org.example

import analyzers.SCA
import analyzers.SCAImpl
import astn.AST
import formatter.Formatter
import formatter.FormatterImpl
import impl.ParserImpl
import interfaces.Parser
import interpreter.Interpreter
import interpreter.InterpreterImpl
import interpreter.Value
import lexer.LexerImpl
import lexer.TokenRegexRule
import org.example.lexer.Lexer
import org.example.utils.JSONManager
import java.io.File
import java.io.FileNotFoundException
import java.util.LinkedList
import java.util.Queue

/**
 * This class is responsible for executing a PrintScript program.
 * It uses a LexerImpl object to lex the program, a ParserImpl object to parse the lexed tokens into an AST,
 * an InterpreterImpl object to interpret the AST, and a FormatterImpl object to format the AST.
 * It also uses a SCAImpl object to perform static code analysis on the AST.
 *
 * @throws FileNotFoundException If the file specified by the path does not exist.
 * @throws Exception If an error occurs while executing the script or if the SCA finds any issues with the AST.
 */

class PrintScript() {
    private var lexer: Lexer =
        LexerImpl(loadConfig("lexerRules.json", "src/main/resources/LexerFullRules.json"))
    private val parser: Parser = ParserImpl()
    private val outputs: Queue<String> = LinkedList()
    private val interpreter: Interpreter = InterpreterImpl({ loadInput() }, { enterIfScope() }, { mergeScopes() })
    private val sca: SCA =
        SCAImpl(loadConfig("scaConfig.json", "src/main/resources/SCADefault.json"))
    private var formatter: Formatter =
        FormatterImpl(
            loadConfig("formatterConfig.json", "src/main/resources/FormatterDefault.json"),
        )

    private var storedVariables: List<MutableMap<String, Value>> = listOf(mutableMapOf())

    fun start(
        path: String,
        inputPath: String,
    ): String {
        addLinesToQueue(inputPath)
        return processFile(path) { line, numberLine ->
            interpreter.readAST(
                lexAndParse(line, numberLine),
                storedVariables.last(),
            )
        }
    }

    fun start(path: String): String {
        return processFile(path) { line, numberLine ->
            interpreter.readAST(
                lexAndParse(line, numberLine),
                storedVariables.last(),
            )
        }
    }

    fun format(path: String): String {
        return processFile(path) { line, numberLine -> formatter.format(lexAndParse(line, numberLine)) }
    }

    fun analyze(path: String): String {
        return processFile(path) { line, numberLine -> sca.readAst(lexAndParse(line, numberLine)) }
    }

    private fun processFile(
        path: String,
        processLine: (String, Int) -> String,
    ): String {
        val file = File(path)
        val output = mutableListOf<String>()
        if (!file.exists()) {
            throw FileNotFoundException("File not found: $path")
        }
        var numberLine = 1
        file.forEachLine { line ->
            if (line.isBlank()) {
                return@forEachLine
            }
            output.add(processLine(line, numberLine))
            while (!lexer.isLineFinished()) {
                output.add(processLine(line, numberLine))
            }
            numberLine++
        }
        if (lexer.stillHaveTokens()) {
            throw Exception("File does not end with a separator")
        }

        return output.joinToString("")
    }

    fun updateRegexRules(newRulesPath: String) {
        val file = File(newRulesPath)
        if (!file.exists()) {
            throw FileNotFoundException("File not found: $newRulesPath")
        }
        val json = file.readText()
        val newRegexRules = JSONManager.jsonToMap<TokenRegexRule>(json)
        lexer = LexerImpl(newRegexRules)

        saveConfig("lexerRules.json", newRulesPath)
    }

    fun changeFormatterConfig(configFilePath: String) {
        val file = File(configFilePath)
        if (!file.exists()) {
            throw FileNotFoundException("File not found: $configFilePath")
        }
        val json = file.readText()
        val newProperties = JSONManager.jsonToMap<String>(json)
        formatter =
            FormatterImpl(
                newProperties,
            )

        saveConfig("formatterConfig.json", configFilePath)
    }

    fun changeSCAConfig(configFilePath: String) {
        val file = File(configFilePath)
        if (!file.exists()) {
            throw FileNotFoundException("File not found: $configFilePath")
        }
        val json = file.readText()
        val newProperties = JSONManager.jsonToMap<Boolean>(json)
        sca.buildSCA(newProperties)

        saveConfig("scaConfig.json", configFilePath)
    }

    private fun lexAndParse(
        line: String,
        numberLine: Int,
    ): AST {
        val tokens = lexer.lex(line, numberLine)
        return parser.parse(tokens)
    }

    private fun enterIfScope() {
        val newStoredVariables = mutableListOf<MutableMap<String, Value>>()
        newStoredVariables.addAll(storedVariables)
        newStoredVariables.add(storedVariables.last().toMutableMap())
        storedVariables = newStoredVariables
    }

    private fun mergeScopes() {
        val hasToUpdate = storedVariables[storedVariables.size - 2]
        val updated = storedVariables.last()

        for ((key, value) in updated) {
            if (hasToUpdate.containsKey(key)) {
                hasToUpdate[key] = value
            }
        }

        storedVariables = storedVariables.dropLast(1)
    }

    private fun addLinesToQueue(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            file.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    outputs.add(line)
                }
            }
        } else {
            return
        }
    }

    private fun loadInput(): String {
        return if (outputs.isEmpty()) {
            ""
        } else {
            outputs.remove()
        }
    }

    private inline fun <reified T> loadConfig(
        configFileName: String,
        defaultConfigPath: String,
    ): Map<String, T> {
        var file = File(configFileName)
        if (!file.exists()) {
            file = File(defaultConfigPath)
        }
        val json = file.readText()
        return JSONManager.jsonToMap<T>(json)
    }

    private fun saveConfig(
        configFileName: String,
        newConfigPath: String,
    ) {
        val file = File(configFileName)
        file.writeText(File(newConfigPath).readText())
    }
}
