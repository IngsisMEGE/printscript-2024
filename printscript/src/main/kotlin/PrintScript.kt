package org.example

import astn.AST
import formatter.FormatterImpl
import impl.ParserImpl
import interfaces.Parser
import interpreter.InterpreterImpl
import interpreter.Value
import lexer.LexerImpl
import lexer.TokenRegexRule
import org.example.utils.JSONManager
import rules.AssignationRule
import rules.MethodRule
import rules.VarDeclarationAssignationRule
import rules.VarDeclarationRule
import java.io.File
import java.io.FileNotFoundException

/**
 * This class is responsible for executing a PrintScript program.
 * It uses a TemporalLexer object to lex the program, a ParserImpl object to parse the lexed tokens into an AST, and a RegularInterpreter object to interpret the AST.
 *
 * @throws FileNotFoundException If the file specified by the path does not exist.
 * @throws Exception If an error occurs while executing the script.
 */

class PrintScript {
    private var lexer = LexerImpl(getLexerDefaultRules())
    private val parser: Parser = ParserImpl()
    private val interpreter = InterpreterImpl()
    private var formatter =
        FormatterImpl(
            mapOf(),
            listOf(
                VarDeclarationAssignationRule("DotFront", "DotBack", "EqualFront", "EqualBack"),
                MethodRule("ammountOfLines"),
                VarDeclarationRule("SpaceInFront", "SpaceInBack"),
                AssignationRule("EqualFront", "EqualBack"),
            ),
        )

    private val storedVariables = mutableMapOf<String, Value>()

    fun start(path: String): String {
        return processFile(path) { line, numberLine -> interpreter.readAST(lexAndParse(line, numberLine), storedVariables) }
    }

    fun format(path: String): String {
        return processFile(path) { line, numberLine -> formatter.format(lexAndParse(line, numberLine)) }
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

            return output.joinToString("")
        } catch (e: Exception) {
            return "An error occurred while executing the script. ${e.message}"
        }
    }

    fun updateRegexRules(newRules: String) {
        val rulesmap = JSONManager.jsonToMap<TokenRegexRule>(newRules)
        lexer = LexerImpl(rulesmap)
    }

    private fun getLexerDefaultRules(): Map<String, TokenRegexRule> {
        var file = File("src/main/resources/LexerDefaultRegex.json")
        if (!file.exists()) {
            file = File("PrintScript/src/main/resources/LexerDefaultRegex.json")
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
                listOf(
                    VarDeclarationAssignationRule("DotFront", "DotBack", "EqualFront", "EqualBack"),
                    MethodRule("ammountOfLines"),
                    VarDeclarationRule("DotFront", "DotBack"),
                    AssignationRule("EqualFront", "EqualBack"),
                ),
            )
    }

    private fun lexAndParse(
        line: String,
        numberLine: Int,
    ): AST {
        val tokens = lexer.lex(line, numberLine)
        return parser.parse(tokens)
    }
}
