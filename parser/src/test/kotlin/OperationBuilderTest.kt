import astBuilders.OperationBuilder
import astn.OperationHead
import astn.OperationNumber
import astn.OperationString
import astn.OperationVariable
import exceptions.SyntacticError
import exceptions.UnexpectedTokenException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token

class OperationBuilderTest {
    private val operationBuilder = OperationBuilder()

    @Test
    fun testBuildWithInvalidTokensShouldThrowException() {
        val tokens =
            listOf(
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(0, 1)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 2), Pair(0, 3)),
            )
        assertThrows(Exception::class.java) {
            OperationBuilder().buildOperation(tokens)
        }
    }

    @Test
    fun testbuildOperationwithunexpectedtoken() {
        val tokens =
            listOf(
                Token(DataType.UNKNOWN, "unknown", Pair(0, 0), Pair(0, 7)),
            )
        assertThrows<UnexpectedTokenException> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun testComplexOperationShouldBeCorrectlyBuilt() {
        val tokens =
            listOf(
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(0, 1)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 2), Pair(0, 3)),
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(0, 4), Pair(0, 5)),
                Token(DataType.NUMBER_VALUE, "3", Pair(0, 6), Pair(0, 7)),
                Token(DataType.OPERATOR_MINUS, "-", Pair(0, 8), Pair(0, 9)),
                Token(DataType.NUMBER_VALUE, "2", Pair(0, 10), Pair(0, 11)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(0, 12), Pair(0, 13)),
            )
        val result = operationBuilder.buildOperation(tokens)

        assertTrue(result is OperationHead)
        val head = result as OperationHead
        assertTrue(head.left is OperationNumber)
        assertTrue(head.right is OperationHead)
        val right = head.right as OperationHead
        assertTrue(right.left is OperationNumber)
        assertTrue(right.right is OperationNumber)
    }

    @Test
    fun testBuildOperationWithVariableNameShouldBeCorrectIfSameTypeVariable() { // Add variable test
        val tokens =
            listOf(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(0, 1)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 2), Pair(0, 3)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 4), Pair(0, 5)),
            )
        val result = operationBuilder.buildOperation(tokens)

        assertTrue(result is OperationHead)
        val head = result as OperationHead
        assertTrue(head.left is OperationVariable)
        assertTrue(head.right is OperationNumber)
    }

    @Test
    fun testBuildOperationWithStringValue() {
        val tokens =
            listOf(
                Token(DataType.STRING_VALUE, "Hello", Pair(0, 0), Pair(0, 5)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 6), Pair(0, 7)),
                Token(DataType.STRING_VALUE, "World", Pair(0, 8), Pair(0, 13)),
            )
        val result = operationBuilder.buildOperation(tokens)

        assertTrue(result is OperationHead)
        val head = result as OperationHead
        assertTrue(head.left is OperationString)
        assertTrue(head.right is OperationString)
    }

    @Test
    fun testBuildOperationWithInvalidOperator() {
        val tokens =
            listOf(
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(0, 1)),
                Token(DataType.UNKNOWN, "unknown", Pair(0, 2), Pair(0, 9)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 10), Pair(0, 11)),
            )
        assertThrows<UnexpectedTokenException> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun testBuildOperationWithInvalidValue() {
        val tokens =
            listOf(
                Token(DataType.UNKNOWN, "unknown", Pair(0, 0), Pair(0, 7)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 8), Pair(0, 9)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 10), Pair(0, 11)),
            )
        assertThrows<UnexpectedTokenException> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun testBuildOperationWithParenthesisNumberAndOperator() {
        val tokens =
            listOf(
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(0, 0), Pair(0, 1)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 2), Pair(0, 3)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 4), Pair(0, 5)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(0, 8), Pair(0, 9)),
            )
        assertThrows<SyntacticError> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun testBuildOperationWithNumberLeftOfLeftParenthesis() {
        val tokens =
            listOf(
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(0, 1)),
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(0, 2), Pair(0, 3)),
                Token(DataType.NUMBER_VALUE, "3", Pair(0, 4), Pair(0, 5)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 6), Pair(0, 7)),
                Token(DataType.NUMBER_VALUE, "2", Pair(0, 8), Pair(0, 9)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(0, 10), Pair(0, 11)),
            )
        assertThrows<SyntacticError> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun testBuildOperationWithNumberRightOfRightParenthesis() {
        val tokens =
            listOf(
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(0, 0), Pair(0, 1)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 2), Pair(0, 3)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 4), Pair(0, 5)),
                Token(DataType.NUMBER_VALUE, "3", Pair(0, 6), Pair(0, 7)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(0, 8), Pair(0, 9)),
                Token(
                    DataType.NUMBER_VALUE,
                    "2",
                    Pair(0, 10),
                    Pair(0, 11),
                ),
            )
        assertThrows<SyntacticError> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun testBuildOperationWithOddParenthesis() {
        val tokens =
            listOf(
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(0, 0), Pair(0, 1)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 2), Pair(0, 3)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 4), Pair(0, 5)),
                Token(DataType.NUMBER_VALUE, "3", Pair(0, 6), Pair(0, 7)),
            )
        assertThrows<SyntacticError> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun testBuildOperationWithTwoOperatorsFollowed() {
        val tokens =
            listOf(
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(0, 1)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 2), Pair(0, 3)),
                Token(DataType.OPERATOR_MINUS, "-", Pair(0, 4), Pair(0, 5)),
                Token(DataType.NUMBER_VALUE, "3", Pair(0, 6), Pair(0, 7)),
            )
        assertThrows<SyntacticError> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun testBuildOperationStartingWithOperator() {
        val tokens =
            listOf(
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(0, 1)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 2), Pair(0, 3)),
                Token(DataType.OPERATOR_MINUS, "-", Pair(0, 4), Pair(0, 5)),
                Token(DataType.NUMBER_VALUE, "3", Pair(0, 6), Pair(0, 7)),
            )
        assertThrows<SyntacticError> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun testBuildOperationWithOperatorAtTheEnd() {
        val tokens =
            listOf(
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(0, 1)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 2), Pair(0, 3)),
                Token(DataType.NUMBER_VALUE, "3", Pair(0, 4), Pair(0, 5)),
                Token(DataType.OPERATOR_MINUS, "-", Pair(0, 6), Pair(0, 7)),
            )
        assertThrows<SyntacticError> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun testBuildOperationWithRightParenthesisBeforeLeftParenthesis() {
        val tokens =
            listOf(
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(0, 1)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(0, 2), Pair(0, 3)),
                Token(DataType.NUMBER_VALUE, "3", Pair(0, 6), Pair(0, 7)),
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(0, 4), Pair(0, 5)),
            )
        assertThrows<UnexpectedTokenException> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun test001StringMultiplyNumberShouldException() {
        val tokens =
            listOf(
                Token(DataType.STRING_VALUE, "Hello", Pair(0, 0), Pair(0, 5)),
                Token(DataType.OPERATOR_MULTIPLY, "*", Pair(0, 6), Pair(0, 7)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 8), Pair(0, 9)),
            )
        assertThrows<SyntacticError> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun test002StringDivideNumberShouldException() {
        val tokens =
            listOf(
                Token(DataType.STRING_VALUE, "Hello", Pair(0, 0), Pair(0, 5)),
                Token(DataType.OPERATOR_DIVIDE, "/", Pair(0, 6), Pair(0, 7)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 8), Pair(0, 9)),
            )
        assertThrows<SyntacticError> {
            operationBuilder.buildOperation(tokens)
        }
    }

    @Test
    fun test003OperationBetweenNumbersThenPlusStringShouldWorkFine() {
        val tokens =
            listOf(
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(0, 1)),
                Token(DataType.OPERATOR_MULTIPLY, "*", Pair(0, 2), Pair(0, 3)),
                Token(DataType.NUMBER_VALUE, "3", Pair(0, 4), Pair(0, 5)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 6), Pair(0, 7)),
                Token(DataType.STRING_VALUE, "Hello", Pair(0, 4), Pair(0, 9)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 10), Pair(0, 11)),
                Token(DataType.STRING_VALUE, "World", Pair(0, 12), Pair(0, 17)),
            )
        val result = operationBuilder.buildOperation(tokens)

        assertTrue(result is OperationHead)
        val head = result as OperationHead
        assertTrue(head.left is OperationHead)
        assertTrue(head.right is OperationString)
    }
}
