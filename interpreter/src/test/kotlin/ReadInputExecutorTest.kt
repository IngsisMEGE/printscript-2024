import interpreter.Value
import interpreter.VariableType
import interpreter.executors.operationMethod.LoadInputHolder
import interpreter.executors.operationMethod.ReadInputExecutor
import org.junit.jupiter.api.Test
import token.DataType
import token.Token
import java.util.Optional
import kotlin.test.assertEquals

var simulateInput: String = "Hello"

private fun readInputTest(): String {
    return simulateInput
}

class ReadInputExecutorTest {
    init {
        LoadInputHolder.loadInput = { readInputTest() }
    }

    @Test
    fun test001ReadInputExecutorWorkCorrectly() {
        simulateInput = "Hello"
        val operationMethod = ReadInputExecutor()
        val parameter = Value(VariableType.STRING, Optional.empty(), true)
        val token = Token(DataType.METHOD_CALL, "readInput", Pair(0, 0), Pair(5, 0))
        val result = operationMethod.execute(token, listOf(parameter), VariableType.STRING)

        assert(result.getValue().isNotEmpty())
    }

    @Test
    fun test002ReadInputNumberParameter() {
        simulateInput = "5"
        val operationMethod = ReadInputExecutor()
        val parameter = Value(VariableType.STRING, Optional.of("Hi"), true)
        val token = Token(DataType.METHOD_CALL, "readInput", Pair(0, 0), Pair(5, 0))
        assertEquals("5", operationMethod.execute(token, listOf(parameter), VariableType.NUMBER).getValue())
        assertEquals(VariableType.NUMBER, operationMethod.execute(token, listOf(parameter), VariableType.NUMBER).getType())
    }

    @Test
    fun test003ReadInputBooleanParameter() {
        simulateInput = "true"
        val operationMethod = ReadInputExecutor()
        val parameter = Value(VariableType.STRING, Optional.of("Hi"), true)
        val token = Token(DataType.METHOD_CALL, "readInput", Pair(0, 0), Pair(5, 0))
        assertEquals("true", operationMethod.execute(token, listOf(parameter), VariableType.BOOLEAN).getValue())
        assertEquals(VariableType.BOOLEAN, operationMethod.execute(token, listOf(parameter), VariableType.BOOLEAN).getType())
    }

    @Test
    fun test004NoParametersShouldError() {
        simulateInput = "true"
        val operationMethod = ReadInputExecutor()
        val token = Token(DataType.METHOD_CALL, "readInput", Pair(0, 0), Pair(5, 0))
        try {
            operationMethod.execute(token, listOf(), VariableType.BOOLEAN)
        } catch (e: IllegalArgumentException) {
            assertEquals("readInput only takes 1 argument but 0 were provided Line 0 : 0.", e.message)
        }
    }

    @Test
    fun test005ParametersIsNotString() {
        simulateInput = "true"
        val operationMethod = ReadInputExecutor()
        val parameter = Value(VariableType.NUMBER, Optional.of("Hi"), true)
        val token = Token(DataType.METHOD_CALL, "readInput", Pair(0, 0), Pair(5, 0))
        try {
            operationMethod.execute(token, listOf(parameter), VariableType.BOOLEAN)
        } catch (e: IllegalArgumentException) {
            assertEquals("readInput only takes String as argument but NUMBER was provided Line 0 : 0.", e.message)
        }
    }
}