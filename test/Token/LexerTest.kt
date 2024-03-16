package Token

import lexer.TemporalLexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.DataType
import token.Token
import javax.xml.crypto.Data

class LexerTest {

    val temporalLexer : TemporalLexer= TemporalLexer()

    @Test
    fun lexTest() {
        val line = "let a;"
        val result = temporalLexer.lex(line, 1)

        assertEquals(DataType.LET_KEYWORD, result[0].getType())
        assertEquals("", result[0].getValue())
        assertEquals(DataType.VARIABLE_NAME, result[1].getType())
        assertEquals("a", result[1].getValue())
    }

    @Test
    fun lexTest2() {
        val line = "let letter = 5;"
        val result = temporalLexer.lex(line, 1)

        assertEquals(DataType.LET_KEYWORD, result[0].getType())
        assertEquals("", result[0].getValue())
        assertEquals(DataType.VARIABLE_NAME, result[1].getType())
        assertEquals("letter", result[1].getValue())
        assertEquals(DataType.ASIGNATION_EQUALS, result[2].getType())
        assertEquals("", result[2].getValue())
        assertEquals(DataType.NUMBER_VALUE, result[3].getType())
        assertEquals("5", result[3].getValue())
    }

    //Corregir test para que sum lo pase solo y depsues  ( como token y todo el resto individualmente
    @Test
    fun methodCall(){
        val line = "let letter = sum(5, 5);"
        val result = temporalLexer.lex(line, 1)

        assertEquals(DataType.LET_KEYWORD, result[0].getType())
        assertEquals("", result[0].getValue())
        assertEquals(DataType.VARIABLE_NAME, result[1].getType())
        assertEquals("letter", result[1].getValue())
        assertEquals(DataType.ASIGNATION_EQUALS, result[2].getType())
        assertEquals("", result[2].getValue())
        assertEquals(DataType.METHOD_CALL, result[3].getType())
        assertEquals("sum(5, 5)", result[3].getValue())
    }
}