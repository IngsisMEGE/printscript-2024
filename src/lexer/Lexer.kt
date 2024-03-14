package lexer

import Token.Token
import lexer.generatorTree.IGeneratorTokenTree

class Lexer(private val tokenGenerators: List<IGeneratorTokenTree>) : LexerInterface {
    override fun lex(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var index = 0
        while (index < input.length) {
            val token = tokenGenerators.mapNotNull { it.generateToken(input, index) }.flatten().maxByOrNull { it.value.length }
            if (token != null) {
                tokens.add(token)
                index += token.value.length
            } else {
                index++
            }
        }
        return tokens
    }
}