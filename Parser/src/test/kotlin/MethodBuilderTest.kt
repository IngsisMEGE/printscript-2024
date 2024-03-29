import ASTN.Method
import ASTN.OperationHead
import ASTN.OperationVariable
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import Parser.ASTBuilders.MethodBuilder
import Parser.exceptions.UnexpectedTokenException
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token

class MethodBuilderTest {

    private val methodBuilder = MethodBuilder()



    @Test
    fun testMethodCallShouldCorrectlyInterpretMethodCall() {
        val tokens = listOf(
            Token(DataType.METHOD_CALL, "print", Pair(0,0), Pair(0,5)),
            Token(DataType.LEFT_PARENTHESIS, "", Pair(0,6), Pair(0,7)),
            Token(DataType.VARIABLE_NAME, "x", Pair(0,8), Pair(0,9)),
            Token(DataType.RIGHT_PARENTHESIS, "", Pair(0,10), Pair(0,11))
        )
        val result = methodBuilder.build(tokens)

        assertTrue(result is Method)
        val methodCall = result as Method
        assertTrue(methodCall.value is OperationVariable)

    }

    @Test
    fun testMethodCallShouldCorrectlyInterpretMethodCallWithComplexOperation() {
        val tokens = listOf(
            Token(DataType.METHOD_CALL, "print", Pair(0,0), Pair(0,5)),
            Token(DataType.LEFT_PARENTHESIS, "", Pair(0,6), Pair(0,7)),
            Token(DataType.NUMBER_VALUE, "5", Pair(0,8), Pair(0,9)),
            Token(DataType.OPERATOR_PLUS, "+", Pair(0,10), Pair(0,11)),
            Token(DataType.NUMBER_VALUE, "3", Pair(0,12), Pair(0,13)),
            Token(DataType.RIGHT_PARENTHESIS, "", Pair(0,14), Pair(0,15))
        )
        val result = methodBuilder.build(tokens)

        assertTrue(result is Method)
        val methodCall = result as Method
        assertEquals(methodCall.methodName.getValue(),"print")
        assertTrue(methodCall.value is OperationHead)
    }

    @Test
    fun testMethodCallShouldCorrectlyInterpretMethodCallWithComplexOperationAndVariable() {
        val tokens = listOf(
            Token(DataType.UNKNOWN, "unknown", Pair(0,0), Pair(0,7)),
            Token(DataType.LEFT_PARENTHESIS, "", Pair(0,8), Pair(0,9)),
            Token(DataType.STRING_VALUE, "Hello", Pair(0,10), Pair(0,15)),
            Token(DataType.RIGHT_PARENTHESIS, "", Pair(0,16), Pair(0,17))
        )
        assertThrows<UnexpectedTokenException> {
            methodBuilder.build(tokens)
        }
    }

    @Test
    fun testMethodCallShouldThrowExceptionIfMethodCallIsNotValid() {
        val tokens = listOf(
            Token(DataType.METHOD_CALL, "print", Pair(0,0), Pair(0,5)),
            Token(DataType.LEFT_PARENTHESIS, "", Pair(0,6), Pair(0,7)),
            Token(DataType.UNKNOWN, "unknown", Pair(0,8), Pair(0,15)),
            Token(DataType.RIGHT_PARENTHESIS, "", Pair(0,16), Pair(0,17))
        )
        assertThrows<UnexpectedTokenException> {
            methodBuilder.build(tokens)
        }
    }
}