package lexer

import Token.Token
import lexer.generatorTree.GeneratorTokenTree

class Lexer(private val tokenGenerators: List<GeneratorTokenTree>) : LexerInterface {
    override fun lex(input: String): List<Token> {

        return emptyList()
    }
}