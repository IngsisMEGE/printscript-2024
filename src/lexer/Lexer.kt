package lexer

import token.Token

class Lexer(private val tokenGenerators: List<RegexTokenGenerator>) : LexerInterface {
    override fun lex(line: String, numberLine: Int): List<Token> {
        val tokens = mutableListOf<Token>()
        tokenGenerators.forEach { tokenGenerator ->
            tokens.addAll(tokenGenerator.generateTokens(line, numberLine))
        }
        return ListTokenManager.orderTokens(tokens)
    }
}
