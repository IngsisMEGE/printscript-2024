import astn.OperationBoolean
import astn.OperationHead
import astn.OperationMethod
import astn.OperationNumber
import astn.OperationString
import interpreter.VariableType
import interpreter.executors.BinaryOperatorReader
import interpreter.executors.operationMethod.LoadInputHolder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.Test

fun input(message: String): String {
    return message
}

class BinaryOperationReaderTest {
    init {
        LoadInputHolder.loadInput = { input("Hello") }
    }

    @Test
    fun test001EvaluateOperationNumber() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation = OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0)))
        val result = binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.STRING)
        assertEquals("5", result.getValue())
    }

    @Test
    fun test002EvaluateOperationString() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation = OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(0, 0), Pair(1, 0)))
        val result = binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.STRING)
        assertEquals("Hello", result.getValue())
    }

    @Test
    fun test003EvaluateOperationHead() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation =
            OperationHead(
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0))),
                OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0))),
            )
        val result = binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.NUMBER)
        assertEquals(VariableType.NUMBER, result.getType())
        assertEquals("10", result.getValue())
    }

    @Test
    fun test004LargeOperationHead() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation =
            OperationHead(
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                OperationHead(
                    Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                    OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0))),
                    OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0))),
                ),
                OperationHead(
                    Token(DataType.OPERATOR_MULTIPLY, "*", Pair(0, 0), Pair(1, 0)),
                    OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0))),
                    OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0))),
                ),
            )
        val result = binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.NUMBER)
        assertEquals("35", result.getValue())
        assertEquals(VariableType.NUMBER, result.getType())
    }

    @Test
    fun test005LargeOperationHeadWithString() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation =
            OperationHead(
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                OperationHead(
                    Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                    OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0))),
                    OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0))),
                ),
                OperationHead(
                    Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                    OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(0, 0), Pair(1, 0))),
                    OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0))),
                ),
            )
        val result = binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.STRING)
        assertEquals("10Hello5", result.getValue())
        assertEquals(VariableType.STRING, result.getType())
    }

    @Test
    fun test007OperationsBetweenBooleanPlus() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation =
            OperationHead(
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                OperationBoolean(Token(DataType.BOOLEAN_VALUE, "true", Pair(0, 0), Pair(1, 0))),
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "false", Pair(0, 0), Pair(1, 0)),
                ),
            )

        assertThrows<Exception> {
            binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.STRING)
        }
    }

    @Test
    fun test008BooleanAndNumberShouldThrowError() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation =
            OperationHead(
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                OperationBoolean(Token(DataType.BOOLEAN_VALUE, "true", Pair(0, 0), Pair(1, 0))),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0)),
                ),
            )

        assertThrows<Exception> {
            binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.STRING)
        }
    }

    @Test
    fun test009BooleanAndStringShouldNotBeCorrect() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation =
            OperationHead(
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                OperationBoolean(Token(DataType.BOOLEAN_VALUE, "true", Pair(0, 0), Pair(1, 0))),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(0, 0), Pair(1, 0)),
                ),
            )

        assertThrows<Exception> {
            binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.STRING)
        }
    }

    @Test
    fun test010CalculateNumberMultiply() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation =
            OperationHead(
                Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0)),
                ),
                OperationHead(
                    Token(DataType.OPERATOR_MULTIPLY, "*", Pair(0, 0), Pair(1, 0)),
                    OperationNumber(
                        Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0)),
                    ),
                    OperationNumber(
                        Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0)),
                    ),
                ),
            )
        val result = binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.NUMBER)
        assertEquals("30", result.getValue())
        assertEquals(VariableType.NUMBER, result.getType())
    }

    @Test
    fun test011CalculateNumberDivide() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation =
            OperationHead(
                Token(DataType.OPERATOR_DIVIDE, "/", Pair(0, 0), Pair(1, 0)),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0)),
                ),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0)),
                ),
            )
        val result = binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.NUMBER)
        assertEquals("1", result.getValue())
        assertEquals(VariableType.NUMBER, result.getType())
    }

    @Test
    fun test012CalculateNumberMinus() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation =
            OperationHead(
                Token(DataType.OPERATOR_MINUS, "-", Pair(0, 0), Pair(1, 0)),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0)),
                ),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0)),
                ),
            )
        val result = binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.NUMBER)
        assertEquals("0", result.getValue())
        assertEquals(VariableType.NUMBER, result.getType())
    }

    @Test
    fun test013MethodCall() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation =
            OperationMethod(
                Token(DataType.METHOD_CALL, "readInput", Pair(0, 0), Pair(1, 0)),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(0, 0), Pair(1, 0)),
                ),
            )
        val result = binaryOperationReader.evaluate(operation, mutableMapOf(), VariableType.STRING)
        assertEquals("Hello", result.getValue())
        assertEquals(VariableType.STRING, result.getType())
    }
}
