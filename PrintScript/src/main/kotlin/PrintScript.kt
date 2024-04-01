
package org.example

import impl.ParserImpl
import interpreter.RegularInterpreter
import org.example.lexer.TemporalLexer
import java.io.File
import java.io.FileNotFoundException

/**
 * This class is responsible for executing a PrintScript program.
 * It uses a TemporalLexer object to lex the program, a ParserImpl object to parse the lexed tokens into an AST, and a RegularInterpreter object to interpret the AST.
 *
 * @throws FileNotFoundException If the file specified by the path does not exist.
 * @throws Exception If an error occurs while executing the script.
 */

class PrintScript(
    private val lexer: TemporalLexer = TemporalLexer(),
    private val parser: ParserImpl = ParserImpl(),
    private val interpreter: RegularInterpreter = RegularInterpreter(),
) {
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
}
