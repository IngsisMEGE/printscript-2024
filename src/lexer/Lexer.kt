package lexer

import token.Token


class Lexer(private val tokenGenerator : List<RegexTokenGenerator>) : LexerInterface {
    override fun lex(line: String, numberLine : Int): List<Token> {
        val tokens = mutableListOf<Token>()
        tokenGenerator.forEach { tokenGenerator ->
            val token = tokenGenerator.generateToken(line, numberLine)
            tokens.addAll(token)
        }
        return tokens
    }
}