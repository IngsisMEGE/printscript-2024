package Token
import org.junit.jupiter.api.Test
import lexer.TemporalLexer
import org.junit.jupiter.api.Assertions.*
import token.DataType

class RegexTest {

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
    }

    @Test
    fun testOperatorPlus() {
        val lexer = TemporalLexer()
        val tokens = lexer.lex("3+2", 1)
        assertEquals(DataType.OPERATOR_PLUS, tokens[1].getType())
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

//    @Test
//fun testStringInsideString() {
//    val lexer = TemporalLexer()
//    val tokens = lexer.lex("let name: String = \"Hello World\"", 1)
//    val expectedTypes = listOf(DataType.LET_KEYWORD, DataType.VARIABLE_NAME, DataType.COLON, DataType.TYPE_KEYWORD, DataType.ASIGNATION_EQUALS, DataType.STRING_VALUE)
//    assertEquals(expectedTypes, tokens.map { it.getType() })
//}
}
