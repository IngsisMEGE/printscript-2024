package lexer

import token.Token


class Lexer(private val TokenGenerator : List<RegexTokenGenerator>) : LexerInterface {
    override fun lex(line: String, numberLine : Int): List<Token> {
        val tokens = mutableListOf<Token>()
        TokenGenerator.forEach { tokenGenerator ->
            val token = tokenGenerator.generateToken(line, numberLine)
            if (token != null) {
                tokens.add(token)
            }
        }
        return tokens
    }
}