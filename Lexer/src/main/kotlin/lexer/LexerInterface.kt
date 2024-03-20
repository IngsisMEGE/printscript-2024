package org.example.lexer

import token.Token

interface LexerInterface {
    fun lex(line: String, numberLine : Int): List<Token>
}
