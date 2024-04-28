import astn.Method
import astn.OperationHead
import astn.OperationNumber
import astn.OperationString
import astn.OperationVariable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import token.DataType
import token.Token

class OperationRuleTest {
    @Test
    fun test001_genericLineOnlyNumbers() {
        val ast = OperationNumber(Token(DataType.NUMBER_TYPE, "5", Pair(4, 0), Pair(5, 0)))
        val operationRule = rules.OperationRule()
        val result = operationRule.genericLine(ast)
        assertEquals("5", result)
    }

    @Test
    fun test002_genericLineOperationWithNumbers() {
        val ast =
            OperationHead(
                Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
                OperationNumber(Token(DataType.NUMBER_TYPE, "5", Pair(4, 0), Pair(5, 0))),
                OperationNumber(Token(DataType.NUMBER_TYPE, "5", Pair(4, 0), Pair(5, 0))),
            )
        val operationRule = rules.OperationRule()
        val result = operationRule.genericLine(ast)
        assertEquals("5+5", result)
    }

    @Test
    fun test003_genericLineOfSimpleOperationWithParenthesis() {
        val ast =
            Method(
                Token(DataType.METHOD_CALL, "println", Pair(4, 0), Pair(5, 0)),
                OperationHead(
                    operator = Token(DataType.OPERATOR_MULTIPLY, "*", Pair(4, 0), Pair(5, 0)),
                    left = OperationNumber(Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0))),
                    right =
                        OperationHead(
                            operator = Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
                            left = OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(4, 0), Pair(5, 0))),
                            right = OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(4, 0), Pair(5, 0))),
                        ),
                ),
            )
        val operationRule = rules.OperationRule()
        val result = operationRule.genericLine(ast.value)
        assertEquals("3*(5+5)", result)
    }

    @Test
    fun test004_genericLineOfComplexOperationWithParenthesis() {
        val ast =
            Method(
                Token(DataType.METHOD_CALL, "println", Pair(4, 0), Pair(5, 0)),
                OperationHead(
                    operator = Token(DataType.OPERATOR_MINUS, "-", Pair(4, 0), Pair(5, 0)),
                    left =
                        OperationHead(
                            operator = Token(DataType.OPERATOR_MULTIPLY, "*", Pair(4, 0), Pair(5, 0)),
                            left =
                                OperationHead(
                                    operator = Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
                                    left = OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(4, 0), Pair(5, 0))),
                                    right = OperationNumber(Token(DataType.NUMBER_VALUE, "2", Pair(4, 0), Pair(5, 0))),
                                ),
                            right = OperationNumber(Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0))),
                        ),
                    right =
                        OperationHead(
                            operator = Token(DataType.OPERATOR_MULTIPLY, "*", Pair(4, 0), Pair(5, 0)),
                            left = OperationNumber(Token(DataType.NUMBER_VALUE, "6", Pair(4, 0), Pair(5, 0))),
                            right =
                                OperationHead(
                                    operator = Token(DataType.OPERATOR_DIVIDE, "/", Pair(4, 0), Pair(5, 0)),
                                    left = OperationNumber(Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0))),
                                    right =
                                        OperationHead(
                                            operator = Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
                                            left = OperationNumber(Token(DataType.NUMBER_VALUE, "4", Pair(4, 0), Pair(5, 0))),
                                            right = OperationNumber(Token(DataType.NUMBER_VALUE, "2", Pair(4, 0), Pair(5, 0))),
                                        ),
                                ),
                        ),
                ),
            )
        val operationRule = rules.OperationRule()
        val result = operationRule.genericLine(ast.value)
        assertEquals("(5+2)*3-6*(3/(4+2))", result)
    }

    @Test
    fun test005_genericLineOfOperationWithString() {
        val ast =
            Method(
                Token(DataType.METHOD_CALL, "println", Pair(4, 0), Pair(5, 0)),
                OperationHead(
                    operator = Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
                    left = OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(4, 0), Pair(5, 0))),
                    right = OperationNumber(Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0))),
                ),
            )
        val operationRule = rules.OperationRule()
        val result = operationRule.genericLine(ast.value)
        assertEquals("\"Hello\"+3", result)
    }

    @Test
    fun test006_genericLineOfOperationWithVariable() {
        val ast =
            Method(
                Token(DataType.METHOD_CALL, "println", Pair(4, 0), Pair(5, 0)),
                OperationHead(
                    operator = Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
                    left = OperationNumber(Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0))),
                    right = OperationVariable(Token(DataType.VARIABLE_NAME, "x", Pair(4, 0), Pair(5, 0))),
                ),
            )
        val operationRule = rules.OperationRule()
        val result = operationRule.genericLine(ast.value)
        assertEquals("3+x", result)
    }

    @Test
    fun test007_genericLineOfOperationWithVariableAndString() {
        val ast =
            Method(
                Token(DataType.METHOD_CALL, "println", Pair(4, 0), Pair(5, 0)),
                OperationHead(
                    operator = Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
                    left =
                        OperationString(
                            Token(DataType.STRING_VALUE, "Hello", Pair(4, 0), Pair(5, 0)),
                        ),
                    right =
                        OperationVariable(
                            Token(DataType.VARIABLE_NAME, "x", Pair(4, 0), Pair(5, 0)),
                        ),
                ),
            )
        val operationRule = rules.OperationRule()
        val result = operationRule.genericLine(ast.value)
        assertEquals("\"Hello\"+x", result)
    }

    @Test
    fun test008_genericLineOfOperationWithVariableAndStringAndNumber() {
        val ast =
            Method(
                Token(DataType.METHOD_CALL, "println", Pair(4, 0), Pair(5, 0)),
                OperationHead(
                    operator = Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
                    left =
                        OperationHead(
                            operator = Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
                            left = OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(4, 0), Pair(5, 0))),
                            right = OperationVariable(Token(DataType.VARIABLE_NAME, "x", Pair(4, 0), Pair(5, 0))),
                        ),
                    right = OperationNumber(Token(DataType.NUMBER_VALUE, "3", Pair(4, 0), Pair(5, 0))),
                ),
            )
        val operationRule = rules.OperationRule()
        val result = operationRule.genericLine(ast.value)
        assertEquals("\"Hello\"+x+3", result)
    }

    @Test
    fun test009isRuleValid() {
        val operationRule = rules.OperationRule()
        operationRule.isTheRuleIncluded()
        assertTrue(true)
    }

    @Test
    fun test010EnforceRule() {
        val operationRule = rules.OperationRule()

        val result = operationRule.isTheRuleIncluded().enforceRule("3+3")
        assertEquals("3 + 3", result)
    }

    @Test
    fun test011EnforceRule() {
        val operationRule = rules.OperationRule()
        val result = operationRule.isTheRuleIncluded().enforceRule("3+3+3")
        assertEquals("3 + 3 + 3", result)
    }
}
