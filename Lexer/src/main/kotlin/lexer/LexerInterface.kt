package org.example.lexer

import token.Token

interface LexerInterface {
    fun lex(numberLine: Int): List<Token>

    fun isLineFinished(): Boolean
}
