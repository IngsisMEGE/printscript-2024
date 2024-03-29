import ASTN.OperationNumber
import Parser.impl.ParserImpl
import org.junit.jupiter.api.Test
import token.DataType
import token.Token
import org.junit.jupiter.api.Assertions.*


class OperationRuleTest {
    @Test
    fun test001_genericLineOnlyNumbers() {
        val ast = ASTN.Operation(OperationNumber(Token(DataType.NUMBER_TYPE, "5", Pair(4, 0), Pair(5, 0))))
        val operationRule = Rules.OperationRule()
        val result = operationRule.genericLine(ast)
        assertEquals("5", result)
    }

    @Test
    fun test002_genericLineOperationWithNumbers() {
        val ast = ASTN.Operation(
            ASTN.OperationHead(
                Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
                OperationNumber(Token(DataType.NUMBER_TYPE, "5", Pair(4, 0), Pair(5, 0))),
                OperationNumber(Token(DataType.NUMBER_TYPE, "5", Pair(4, 0), Pair(5, 0)))
            )
        )
        val operationRule = Rules.OperationRule()
        val result = operationRule.genericLine(ast)
        assertEquals("5+5", result)
    }

    @Test
    fun test003_genericLineOfSimpleOperationWithParenthesis() {
        val tokenList = listOf(
            Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_MULTIPLY, "*", Pair(4, 0), Pair(5, 0)),
            Token(DataType.LEFT_PARENTHESIS, "(", Pair(4, 0), Pair(5, 0)),
            Token(DataType.NUMBER_VALUE, "5", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
            Token(DataType.NUMBER_VALUE, "5", Pair(4, 0), Pair(5, 0)),
            Token(DataType.RIGHT_PARENTHESIS, ")", Pair(4, 0), Pair(5, 0))
        )
        val ast = ParserImpl().parse(tokenList)
        val operationRule = Rules.OperationRule()
        val result = operationRule.genericLine(ast)
        assertEquals("3*(5+5)", result)
    }

    @Test
    fun test004_genericLineOfComplexOperationWithParenthesis() {
        val tokenList = listOf(
            Token(DataType.LEFT_PARENTHESIS, "(", Pair(4, 0), Pair(5, 0)),
            Token(DataType.LEFT_PARENTHESIS, "(", Pair(4, 0), Pair(5, 0)),
            Token(DataType.NUMBER_VALUE, "5", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
            Token(DataType.NUMBER_VALUE, "2", Pair(4, 0), Pair(5, 0)),
            Token(DataType.RIGHT_PARENTHESIS, ")", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_MULTIPLY, "*", Pair(4, 0), Pair(5, 0)),
            Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0)),
            Token(DataType.RIGHT_PARENTHESIS, ")", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_MINUS, "-", Pair(4, 0), Pair(5, 0)),
            Token(DataType.NUMBER_VALUE, "6", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_MULTIPLY, "*", Pair(4, 0), Pair(5, 0)),
            Token(DataType.LEFT_PARENTHESIS, "(", Pair(4, 0), Pair(5, 0)),
            Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_DIVIDE, "/", Pair(4, 0), Pair(5, 0)),
            Token(DataType.LEFT_PARENTHESIS, "(", Pair(4, 0), Pair(5, 0)),
            Token(DataType.NUMBER_VALUE, "4", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
            Token(DataType.NUMBER_VALUE, "2", Pair(4, 0), Pair(5, 0)),
            Token(DataType.RIGHT_PARENTHESIS, ")", Pair(4, 0), Pair(5, 0)),
            Token(DataType.RIGHT_PARENTHESIS, ")", Pair(4, 0), Pair(5, 0))
        )
        val ast = ParserImpl().parse(tokenList)
        val operationRule = Rules.OperationRule()
        val result = operationRule.genericLine(ast)
        assertEquals("(5+2)*3-6*(3/(4+2))", result)
    }

    @Test
    fun test005_genericLineOfOperationWithString() {
        val tokenList = listOf(
            Token(DataType.STRING_VALUE, "Hello", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
            Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0))
        )
        val ast = ParserImpl().parse(tokenList)
        val operationRule = Rules.OperationRule()
        val result = operationRule.genericLine(ast)
        assertEquals("\"Hello\"+3", result)
    }

    @Test
    fun test006_genericLineOfOperationWithVariable() {
        val tokenList = listOf(
            Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
            Token(DataType.VARIABLE_NAME, "x", Pair(4, 0), Pair(5, 0))
        )
        val ast = ParserImpl().parse(tokenList)
        val operationRule = Rules.OperationRule()
        val result = operationRule.genericLine(ast)
        assertEquals("3+x", result)
    }

    @Test
    fun test007_genericLineOfOperationWithVariableAndString() {
        val tokenList = listOf(
            Token(DataType.STRING_VALUE, "Hello", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
            Token(DataType.VARIABLE_NAME, "x", Pair(4, 0), Pair(5, 0))
        )
        val ast = ParserImpl().parse(tokenList)
        val operationRule = Rules.OperationRule()
        val result = operationRule.genericLine(ast)
        assertEquals("\"Hello\"+x", result)
    }

    @Test
    fun test008_genericLineOfOperationWithVariableAndStringAndNumber() {
        val tokenList = listOf(
            Token(DataType.STRING_VALUE, "Hello", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
            Token(DataType.VARIABLE_NAME, "x", Pair(4, 0), Pair(5, 0)),
            Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
            Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0))
        )
        val ast = ParserImpl().parse(tokenList)
        val operationRule = Rules.OperationRule()
        val result = operationRule.genericLine(ast)
        assertEquals("\"Hello\"+x+3", result)
    }

    @Test
    fun test009isRuleValid() {
        val operationRule = Rules.OperationRule()
        val result = operationRule.isTheRuleIncluded(mapOf())
        assertTrue(result is Rules.OperationRule)
    }


    @Test
    fun test010EnforceRule() {
        val operationRule = Rules.OperationRule()

        val result = operationRule.isTheRuleIncluded(mapOf()).enforceRule("3+3")
        assertEquals("3 + 3", result)
    }

    @Test
    fun test011EnforceRule() {
        val operationRule = Rules.OperationRule()
        val result = operationRule.isTheRuleIncluded(mapOf()).enforceRule("3+3+3")
        assertEquals("3 + 3 + 3", result)
    }

}