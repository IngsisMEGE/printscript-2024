package lexer

import Token.Token

interface LexerInterface {
    fun lex(input: String): List<Token>
}
