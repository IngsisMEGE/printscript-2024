import interpreter.Value
import interpreter.VariableType
import interpreter.executors.operationMethod.ReadEnvExecutor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import java.util.*
import kotlin.test.assertEquals

class ReadEnvExecutorTest {
    @Test
    fun test001ReadEnvExecutorWorksCorrectly() {
        val operationMethod = ReadEnvExecutor(::parseValue)
        val envName = "test"
        val resultEnv = "Hello"
        System.setProperty(envName, resultEnv)

        val token = Token(DataType.METHOD_CALL, "readEnv", Pair(0, 0), Pair(5, 0))
        val result = operationMethod.execute(token, listOf(Value(VariableType.STRING, Optional.of(envName))), VariableType.STRING)

        assertEquals(resultEnv, result.getValue())

        System.clearProperty(envName)
    }

    @Test
    fun test002ReadEnvExecutorNotWorkParameterIsNotString() {
        val operationMethod = ReadEnvExecutor(::parseValue)
        val envName = "test"
        val resultEnv = "Hello"
        System.setProperty(envName, resultEnv)

        val token = Token(DataType.METHOD_CALL, "readEnv", Pair(0, 0), Pair(5, 0))

        assertThrows<Exception> {
            operationMethod.execute(token, listOf(Value(VariableType.NUMBER, Optional.of("1"))), VariableType.STRING)
        }
    }

    @Test
    fun test003MoreThanOneParameterShouldError() {
        val operationMethod = ReadEnvExecutor(::parseValue)
        val envName = "test"
        val resultEnv = "Hello"
        System.setProperty(envName, resultEnv)

        val token = Token(DataType.METHOD_CALL, "readEnv", Pair(0, 0), Pair(5, 0))

        assertThrows<Exception> {
            operationMethod.execute(token, listOf(Value(VariableType.STRING, Optional.of(envName)), Value(VariableType.STRING, Optional.of(envName))), VariableType.STRING)
        }
    }

    @Test
    fun test004EnvVariableDoesNotExist() {
        val operationMethod = ReadEnvExecutor(::parseValue)
        val envName = "test"

        val token = Token(DataType.METHOD_CALL, "readEnv", Pair(0, 0), Pair(5, 0))

        assertThrows<Exception> {
            operationMethod.execute(token, listOf(Value(VariableType.STRING, Optional.of(envName))), VariableType.STRING)
        }
    }

    private fun parseValue(
        value: String,
        type: VariableType,
    ): Value {
        return when (type) {
            VariableType.STRING -> Value(type, Optional.of(value))
            VariableType.NUMBER -> {
                val number = value.toIntOrNull()
                if (number === null) {
                    throw IllegalArgumentException("El valor $value no es un número válido.")
                } else {
                    Value(type, Optional.of(number.toString()))
                }
            }
            VariableType.BOOLEAN -> {
                val booleanValue = value.toBooleanStrictOrNull()
                if (booleanValue === null) {
                    throw IllegalArgumentException("El valor $value no es un número válido.")
                } else {
                    Value(type, Optional.of(booleanValue.toString()))
                }
            }
        }
    }
}
