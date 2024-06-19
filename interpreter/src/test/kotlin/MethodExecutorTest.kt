import astn.Method
import astn.OperationMethod
import astn.OperationString
import interpreter.executors.MethodExecutor
import interpreter.executors.operationMethod.LoadInputHolder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token

class MethodExecutorTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun setUp() {
            LoadInputHolder.loadInput = ::loadInput
        }

        private fun loadInput(): String {
            return "hel"
        }
    }

    @Test
    fun test001MethodExecutorMethodNotFound() {
        val methodExecutor = MethodExecutor()
        val ast =
            Method(
                Token(DataType.VARIABLE_NAME, "IAMNOTAMETHOD", Pair(0, 0), Pair(6, 0)),
                OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))),
            )
        val exception =
            assertThrows<Exception> {
                methodExecutor.execute(ast, mutableMapOf())
            }
        assertEquals("Method IAMNOTAMETHOD not found at position 0 line 0", exception.message)
    }

    @Test
    fun test002MethodExecutorMethodPrintln() {
        val methodExecutor = MethodExecutor()
        val ast =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))),
            )
        val result = methodExecutor.execute(ast, mutableMapOf())
        assertEquals("Hello\n", result)
    }

    @Test
    fun test003MethodPrintlnReadInput() {
        LoadInputHolder.loadInput = { "Hello" }
        val methodExecutor = MethodExecutor()
        val ast =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationMethod(
                    Token(DataType.VARIABLE_NAME, "readInput", Pair(0, 0), Pair(6, 0)),
                    OperationString(
                        Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0)),
                    ),
                ),
            )

        val result = methodExecutor.execute(ast, mutableMapOf())
        assertEquals("Hello\nHello\n", result)
    }
}
