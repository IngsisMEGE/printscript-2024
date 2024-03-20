package token

import org.example.lexer.TemporalLexer
import token.DataType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LexerTest {

    private val temporalLexer : TemporalLexer= TemporalLexer()

    @Test
    fun lexTest() {
        val line = "let a;"
        val result = temporalLexer.lex(line, 1)
        assertEquals(DataType.LET_KEYWORD, result[0].getType())
        assertEquals("", result[0].getValue())
        assertEquals(DataType.VARIABLE_NAME, result[1].getType())
        assertEquals("a", result[1].getValue())
        assertEquals(4 , result[1].getInitialPosition().first)
    }

    @Test
    fun lexTest2() {
        val line = "let name = 5;"
        val result = temporalLexer.lex(line, 1)

        assertEquals(DataType.LET_KEYWORD, result[0].getType())
        assertEquals("", result[0].getValue())
        assertEquals(DataType.VARIABLE_NAME, result[1].getType())
        assertEquals("name", result[1].getValue())
        assertEquals(DataType.ASIGNATION_EQUALS, result[2].getType())
        assertEquals("", result[2].getValue())
        assertEquals(DataType.NUMBER_VALUE, result[3].getType())
        assertEquals("5", result[3].getValue())
    }

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
        assertEquals("sum", result[3].getValue())
        assertEquals(DataType.LEFT_PARENTHESIS, result[4].getType())
        assertEquals("", result[4].getValue())
        assertEquals(DataType.NUMBER_VALUE, result[5].getType())
        assertEquals("5", result[5].getValue())
        assertEquals(DataType.COMA, result[6].getType())
        assertEquals("", result[6].getValue())
        assertEquals(DataType.NUMBER_VALUE, result[7].getType())
        assertEquals("5", result[7].getValue())
        assertEquals(DataType.RIGHT_PARENTHESIS, result[8].getType())
        assertEquals("", result[8].getValue())

    }

    @Test
    fun testLetKeywordPass() {
        val lexer = TemporalLexer()
        val tokens = lexer.lex("let", 1)
        assertEquals(DataType.LET_KEYWORD, tokens[0].getType())
    }

    @Test
    fun testLetKeywordTricky() {
        val lexer = TemporalLexer()
        val tokens = lexer.lex("let letting", 1)
        assertEquals(DataType.VARIABLE_NAME, tokens[1].getType())
        assertEquals("letting", tokens[1].getValue())
    }

    @Test
    fun testOperatorPlus() {
        val lexer = TemporalLexer()
        val tokens = lexer.lex("3 +2", 1)
        assertEquals(DataType.OPERATOR_PLUS, tokens[1].getType())
        assertEquals("2", tokens[2].getValue())
        assertEquals(3, tokens[2].getInitialPosition().first)
    }


    @Test
    fun testStringLiteral() {
        val lexer = TemporalLexer()
        val tokens = lexer.lex("\"This is a string\"", 1)
        assertEquals(DataType.STRING_VALUE, tokens[0].getType())
    }

    @Test
    fun testMethodCall() {
        val lexer = TemporalLexer()
        val tokens = lexer.lex("println(\"Hello World\")", 1)
        assertEquals(DataType.METHOD_CALL, tokens[0].getType())
    }

    @Test
    fun testNumberValue() {
        val lexer = TemporalLexer()
        val tokens = lexer.lex("42", 1)
        assertEquals(DataType.NUMBER_VALUE, tokens[0].getType())
    }

    @Test
    fun testComplexExpression() {
        val lexer = TemporalLexer()
        val tokens = lexer.lex("3 + 4 * (2 - 1)", 1)
        val expectedTypes = listOf(
            DataType.NUMBER_VALUE,
            DataType.OPERATOR_PLUS,
            DataType.NUMBER_VALUE,
            DataType.OPERATOR_MULTIPLY,
            DataType.LEFT_PARENTHESIS,
            DataType.NUMBER_VALUE,
            DataType.OPERATOR_MINUS,
            DataType.NUMBER_VALUE,
            DataType.RIGHT_PARENTHESIS
        )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }
    @Test
    fun testWhitespaceVariation() {
        val lexer = TemporalLexer()
        val tokens = lexer.lex("let    varName    =    \"value\";", 1)
        assertEquals(DataType.LET_KEYWORD, tokens[0].getType())
        assertEquals(DataType.VARIABLE_NAME, tokens[1].getType())
        assertEquals("varName", tokens[1].getValue())
        assertEquals(DataType.ASIGNATION_EQUALS, tokens[2].getType())
        assertEquals(DataType.STRING_VALUE, tokens[3].getType())
        assertEquals("\"value\"", tokens[3].getValue())
    }

    @Test
    fun testStringWithEscapedCharacters() {
        val lexer = TemporalLexer()
        val tokens = lexer.lex("\"Line1\\nLine2\"", 1)
        assertEquals(DataType.STRING_VALUE, tokens[0].getType())
        assertEquals("\"Line1\\nLine2\"", tokens[0].getValue())
    }


    @Test
    fun testNestedExpressions() {
        val lexer = TemporalLexer()
        val tokens = lexer.lex("let result = (3 + (2 * 5));", 1)
        val expectedTypes = listOf(
            DataType.LET_KEYWORD,
            DataType.VARIABLE_NAME,
            DataType.ASIGNATION_EQUALS,
            DataType.LEFT_PARENTHESIS,
            DataType.NUMBER_VALUE,
            DataType.OPERATOR_PLUS,
            DataType.LEFT_PARENTHESIS,
            DataType.NUMBER_VALUE,
            DataType.OPERATOR_MULTIPLY,
            DataType.NUMBER_VALUE,
            DataType.RIGHT_PARENTHESIS,
            DataType.RIGHT_PARENTHESIS,
            DataType.SEMICOLON
        )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }
}