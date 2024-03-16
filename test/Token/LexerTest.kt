package Token

import lexer.TemporalLexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.DataType
import token.Token

class LexerTest {

    val temporalLexer : TemporalLexer= TemporalLexer()

    @Test
    fun lexTest() {
        val line = "let a;"
        val result = temporalLexer.lex(line, 1)

        val toke1 : Token = Token(DataType.LET_KEYWORD, "let", Pair(1, 1), Pair(1, 3))
        val toke2 : Token = Token(DataType.VARIABLE_NAME, "a", Pair(1, 5), Pair(1, 5))
        val toke3 : Token = Token(DataType.SEMICOLON, ";", Pair(1, 6), Pair(1, 6))

        val expected = listOf(toke1, toke2, toke3)
        assertEquals(expected, result)
    }
}