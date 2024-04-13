import astn.OperationHead
import astn.OperationNumber
import astn.OperationString
import interpreter.VariableType
import interpreter.executors.BinaryOperatorReader
import org.junit.jupiter.api.Assertions.assertEquals
import token.DataType
import token.Token
import kotlin.test.Test

class BinaryOperationReaderTest {
    @Test
    fun test001EvaluateOperationNumber() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation = OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(1, 0)))
        val result = binaryOperationReader.evaluate(operation, mutableMapOf())
        assertEquals("5", result.getValue())
    }

    @Test
    fun test002EvaluateOperationString() {
        val binaryOperationReader = BinaryOperatorReader()
        val operation = OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(0, 0), Pair(1, 0)))
        val result = binaryOperationReader.evaluate(operation, mutableMapOf())
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
        val result = binaryOperationReader.evaluate(operation, mutableMapOf())
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
        val result = binaryOperationReader.evaluate(operation, mutableMapOf())
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
        val result = binaryOperationReader.evaluate(operation, mutableMapOf())
        assertEquals("10Hello5", result.getValue())
        assertEquals(VariableType.STRING, result.getType())
    }
}