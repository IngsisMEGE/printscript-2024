
package org.example

import JSONManager
import impl.ParserImpl
import interfaces.Parser
import interpreter.RegularInterpreter
import lexer.TokenRegexRule
import org.example.lexer.Lexer
import java.io.File
import java.io.FileNotFoundException

/**
 * This class is responsible for executing a PrintScript program.
 * It uses a TemporalLexer object to lex the program, a ParserImpl object to parse the lexed tokens into an AST, and a RegularInterpreter object to interpret the AST.
 *
 * @throws FileNotFoundException If the file specified by the path does not exist.
 * @throws Exception If an error occurs while executing the script.
 */

class PrintScript() {
    private var lexer = Lexer(getLexerDefaultRules())
    private val parser: Parser = ParserImpl()
    private val interpreter = RegularInterpreter()

    fun start(path: String): String {
        val file = File(path)
        val output = mutableListOf<String>()
        if (!file.exists()) {
            throw FileNotFoundException("File not found: $path")
        }
        try {
            file.forEachLine { line ->
                val tokens = lexer.lex(line, 1)
                val ast = parser.parse(tokens)
                output.add(interpreter.readAST(ast))
            }

            return output.joinToString("\n")
        } catch (e: Exception) {
            return "An error occurred while executing the script. ${e.message}"
        }
    }

    fun getInterpreter() = interpreter

    fun getLexer() = lexer

    fun getParser() = parser

    fun updateRegexRules(newRules: Map<String, TokenRegexRule>) {
        lexer = Lexer(newRules)
    }

    private fun getLexerDefaultRules(): Map<String, TokenRegexRule> {
        val file = File("src/main/resources/LexerDefaultRegex.json")
        val json = file.readText()
        return JSONManager.jsonToMap<TokenRegexRule>(json)
    }
}
