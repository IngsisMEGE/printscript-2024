package org.example

import analyzers.SCAImpl
import astn.AST
import formatter.FormatterImpl
import impl.ParserImpl
import interfaces.Parser
import interpreter.InterpreterImpl
import interpreter.Value
import lexer.LexerImpl
import lexer.TokenRegexRule
import org.example.utils.JSONManager
import java.io.File
import java.io.FileNotFoundException

/**
 * This class is responsible for executing a PrintScript program.
 * It uses a LexerImpl object to lex the program, a ParserImpl object to parse the lexed tokens into an AST,
 * an InterpreterImpl object to interpret the AST, and a FormatterImpl object to format the AST.
 * It also uses a SCAImpl object to perform static code analysis on the AST.
 *
 * @throws FileNotFoundException If the file specified by the path does not exist.
 * @throws Exception If an error occurs while executing the script or if the SCA finds any issues with the AST.
 */

class PrintScript(private val loadInput: (String) -> String, private val version: String = "1.0") {

    private var lexer = LexerImpl(getLexerDefaultRules())
    private val parser: Parser = ParserImpl()
    private val interpreter = InterpreterImpl(loadInput, { enterIfScope() }, { mergeScopes() })
    private val sca = SCAImpl(mapOf("CamelCaseFormat" to true, "SnakeCaseFormat" to true, "MethodNoExpresion" to true))
    private var formatter =
        FormatterImpl(
            mapOf(),
        )

    private var storedVariables: List<MutableMap<String, Value>> = listOf(mutableMapOf())

    fun start(path: String): String {
        return processFile(path) { line, numberLine -> interpreter.readAST(lexAndParse(line, numberLine), storedVariables.last()) }
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
        try {
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

            return output.joinToString("").trimEnd('\n')
        } catch (e: Exception) {
            return "An error occurred while executing the script. ${e.message}"
        }
    }

    fun updateRegexRules(newRules: String) {
        val rulesmap = JSONManager.jsonToMap<TokenRegexRule>(newRules)
        lexer = LexerImpl(rulesmap)
    }

    private fun getLexerDefaultRules(): Map<String, TokenRegexRule> {
    val fileName = if (version == "1.0") "LexerDefaultRegex.json" else "LexerFullRules.json"
    var file = File("src/main/resources/$fileName")
    if (!file.exists()) {
        file = File("PrintScript/src/main/resources/$fileName")
    }
    val json = file.readText()
    return JSONManager.jsonToMap<TokenRegexRule>(json)
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
}
