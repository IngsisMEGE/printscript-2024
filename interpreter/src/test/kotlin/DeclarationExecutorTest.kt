import astn.VarDeclaration
import executors.DeclarationExecution
import interpreter.Value
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.Test

class DeclarationExecutorTest {
    @Test
    fun test001DeclarationExecutorVariableAlreadyExists() {
        val declarationExecutor = DeclarationExecution()
        val map = mutableMapOf<String, Value>()
        val ast =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(7, 0), Pair(12, 0)),
                Token(DataType.VARIABLE_NAME, "IAMNOTAVARIABLE", Pair(0, 0), Pair(6, 0)),
                true,
            )
        declarationExecutor.execute(ast, map)
        val exception =
            assertThrows<Exception> {
                declarationExecutor.execute(ast, map)
            }
        assertEquals("Variable 'IAMNOTAVARIABLE' already exists at Line 0", exception.message)
    }

    @Test
    fun test002DeclarationUnexpectedType() {
        val declarationExecutor = DeclarationExecution()
        val map = mutableMapOf<String, Value>()
        val ast =
            VarDeclaration(
                Token(DataType.OPERATOR_MULTIPLY, "number", Pair(7, 0), Pair(12, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val exception =
            assertThrows<Exception> {
                declarationExecutor.execute(ast, map)
            }
        assertEquals("Unexpected type at Line 0", exception.message)
    }

    @Test
    fun test003DeclarationShouldDeclareCorrectlyNumberType() {
        val declarationExecutor = DeclarationExecution()
        val map = mutableMapOf<String, Value>()
        val ast =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(7, 0), Pair(12, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val result = declarationExecutor.execute(ast, map)
        assertEquals("", result)
    }

    @Test
    fun test004DeclarationShouldDeclareCorrectlyStringType() {
        val declarationExecutor = DeclarationExecution()
        val map = mutableMapOf<String, Value>()
        val ast =
            VarDeclaration(
                Token(DataType.STRING_TYPE, "string", Pair(7, 0), Pair(12, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val result = declarationExecutor.execute(ast, map)
        assertEquals("", result)
    }

    @Test
    fun test005DeclarationShouldDeclareCorrectlyBooleanType() {
        val declarationExecutor = DeclarationExecution()
        val map = mutableMapOf<String, Value>()
        val ast =
            VarDeclaration(
                Token(DataType.BOOLEAN_TYPE, "boolean", Pair(7, 0), Pair(12, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val result = declarationExecutor.execute(ast, map)
        assertEquals("", result)
    }

    @Test
    fun testConstDeclarationWithInitialValue() {
        val declarationExecutor = DeclarationExecution()
        val map = mutableMapOf<String, Value>()
        val ast =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(7, 0), Pair(12, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                false,
            )
        val result = declarationExecutor.execute(ast, map)

        assertEquals("", result)
    }
}
