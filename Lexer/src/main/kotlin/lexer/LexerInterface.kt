package org.example.lexer

import org.example.token.Token

interface LexerInterface {
    fun lex(line: String, numberLine : Int): List<Token>
}
