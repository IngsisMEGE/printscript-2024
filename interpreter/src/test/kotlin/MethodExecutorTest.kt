import astn.Method
import astn.OperationString
import interpreter.executors.MethodExecutor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token

class MethodExecutorTest {
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
        assertEquals("Method not found", exception.message)
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
}
