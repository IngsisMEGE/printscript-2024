package lexer

import token.Token


interface LexerInterface {
    fun lex(line: String, numberLine : Int): List<Token>
}
