package lexer

import Token.Token

interface LexerInterface {
    fun lex(line: String, numberLine : Int): List<Token>
}
