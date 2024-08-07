import astn.Assignation
import astn.OperationBoolean
import astn.OperationHead
import astn.OperationNumber
import astn.OperationString
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import executors.DeclarationAssignationExecution
import executors.DeclarationExecution
import interpreter.Value
import interpreter.executors.AssignationExecution
import interpreter.executors.operationMethod.EnvReaderHolder
import interpreter.executors.operationMethod.LoadInputHolder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.Test

class AssignationExecutionTest {
    @BeforeEach
    fun setUp() {
        EnvReaderHolder.envReader = SystemEnvReader()
    }

    @Test
    fun test001AssignationExecutorVariableNotFound() {
        LoadInputHolder.loadInput = ::loadInput
        val assignationExecutor = AssignationExecution()
        val ast =
            Assignation(
                Token(DataType.VARIABLE_NAME, "IAMNOTAVARIABLE", Pair(0, 0), Pair(6, 0)),
                OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))),
            )
        val exception =
            assertThrows<Exception> {
                assignationExecutor.execute(ast, mutableMapOf())
            }
        assertEquals("Variable 'IAMNOTAVARIABLE' not found at Line 0", exception.message)
    }

    @Test
    fun test002AssignationExecutorVariableTypeMismatch() {
        val valDeclarationExecutor = DeclarationExecution()
        LoadInputHolder.loadInput = ::loadInput
        val assignationExecutor = AssignationExecution()
        val map = mutableMapOf<String, Value>()

        val ast1 =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "IAMNOTAVARIABLE", Pair(0, 0), Pair(6, 0)),
                OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))),
            )
        valDeclarationExecutor.execute(ast1, map)
        val exception =
            assertThrows<Exception> {
                assignationExecutor.execute(ast2, map)
            }
        assertEquals("Variable 'IAMNOTAVARIABLE' not found at Line 0", exception.message)
    }

    @Test
    fun test003AssignationExecutorShouldAssignCorrectly() {
        val valDeclarationExecutor = DeclarationExecution()
        LoadInputHolder.loadInput = ::loadInput
        val assignationExecutor = AssignationExecution()
        val map = mutableMapOf<String, Value>()

        val ast1 =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0)),
                ),
            )
        valDeclarationExecutor.execute(ast1, map)
        val result = assignationExecutor.execute(ast2, map)
        assertEquals("", result)
    }

    @Test
    fun test004AssignationExecutorShouldAssignCorrectlyWithStringType() {
        val valDeclarationExecutor = DeclarationExecution()
        LoadInputHolder.loadInput = ::loadInput
        val assignationExecutor = AssignationExecution()
        val map = mutableMapOf<String, Value>()

        val ast1 =
            VarDeclaration(
                Token(DataType.STRING_TYPE, "string", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0)),
                ),
            )
        valDeclarationExecutor.execute(ast1, map)
        val result = assignationExecutor.execute(ast2, map)
        assertEquals("", result)
    }

    @Test
    fun test005AssignationExecutorShouldAssignCorrectlyAComplexExpression() {
        val valDeclarationExecutor = DeclarationExecution()
        LoadInputHolder.loadInput = ::loadInput
        val assignationExecutor = AssignationExecution()
        val map = mutableMapOf<String, Value>()
        val ast1 =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                OperationHead(
                    Token(DataType.OPERATOR_PLUS, "+", Pair(7, 0), Pair(12, 0)),
                    OperationNumber(Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0))),
                    OperationNumber(Token(DataType.NUMBER_VALUE, "2", Pair(7, 0), Pair(12, 0))),
                ),
            )
        valDeclarationExecutor.execute(ast1, map)
        val result = assignationExecutor.execute(ast2, map)
        assertEquals("", result)
    }

    @Test
    fun test006AssignationExecutorShouldConcatenate2String() {
        val valDeclarationExecutor = DeclarationExecution()
        LoadInputHolder.loadInput = ::loadInput
        val assignationExecutor = AssignationExecution()
        val map = mutableMapOf<String, Value>()
        val ast1 =
            VarDeclaration(
                Token(DataType.STRING_TYPE, "string", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                OperationHead(
                    Token(DataType.OPERATOR_PLUS, "+", Pair(7, 0), Pair(12, 0)),
                    OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))),
                    OperationString(Token(DataType.STRING_VALUE, "World", Pair(7, 0), Pair(12, 0))),
                ),
            )

        valDeclarationExecutor.execute(ast1, map)
        val result = assignationExecutor.execute(ast2, map)
        assertEquals("", result)
    }

    @Test
    fun test007AssignationExecutorShouldWorkWithVarDeclarationAssignation() {
        LoadInputHolder.loadInput = ::loadInput
        val valDeclarationAssignation = DeclarationAssignationExecution()
        val assignationExecutor = AssignationExecution()
        val map = mutableMapOf<String, Value>()
        val ast1 =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                    true,
                ),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0)),
                ),
            )

        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0)),
                ),
            )

        valDeclarationAssignation.execute(ast1, map)

        val result = assignationExecutor.execute(ast2, map)
        assertEquals("", result)
    }

    @Test
    fun test007AssignationExecutorShouldCreateStringWithSpace() {
        val valDeclarationExecutor = DeclarationExecution()
        LoadInputHolder.loadInput = ::loadInput
        val assignationExecutor = AssignationExecution()
        val map = mutableMapOf<String, Value>()
        val ast1 =
            VarDeclaration(
                Token(DataType.STRING_TYPE, "string", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                OperationString(Token(DataType.STRING_VALUE, "Hello World", Pair(7, 0), Pair(12, 0))),
            )

        valDeclarationExecutor.execute(ast1, map)
        val result = assignationExecutor.execute(ast2, map)
        assertEquals("", result)
    }

    @Test
    fun test008AssignationOfBooleanShouldBeCorrect() {
        val valDeclarationExecutor = DeclarationExecution()
        LoadInputHolder.loadInput = ::loadInput
        val assignationExecutor = AssignationExecution()
        val map = mutableMapOf<String, Value>()
        val ast1 =
            VarDeclaration(
                Token(DataType.BOOLEAN_TYPE, "boolean", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "true", Pair(7, 0), Pair(12, 0)),
                ),
            )

        valDeclarationExecutor.execute(ast1, map)
        val result = assignationExecutor.execute(ast2, map)
        assertEquals("", result)
    }

    @Test
    fun test009AssignationBooleanWithOtherTypeShouldThrowError() {
        val valDeclarationExecutor = DeclarationExecution()
        LoadInputHolder.loadInput = ::loadInput
        val assignationExecutor = AssignationExecution()
        val map = mutableMapOf<String, Value>()
        val ast1 =
            VarDeclaration(
                Token(DataType.BOOLEAN_TYPE, "boolean", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0)),
                ),
            )

        valDeclarationExecutor.execute(ast1, map)
        val exception =
            assertThrows<Exception> {
                assignationExecutor.execute(ast2, map)
            }
        assertEquals("Variable type mismatch at Line 0 between BOOLEAN and NUMBER", exception.message)
    }

    @Test
    fun testConstAssignation() {
        LoadInputHolder.loadInput = ::loadInput
        val valDeclarationAssignation = DeclarationAssignationExecution()
        val assignationExecutor = AssignationExecution()
        val variables = mutableMapOf<String, Value>()
        val ast1 =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                    false,
                ),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0)),
                ),
            )

        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "5", Pair(7, 0), Pair(12, 0)),
                ),
            )

        valDeclarationAssignation.execute(ast1, variables)

        val exception =
            assertThrows<Exception> {
                assignationExecutor.execute(ast2, variables)
            }

        assertEquals("Cannot assign new value to constant 'x' at Line 0 : 0.", exception.message)
    }

    private fun loadInput(message: String): String {
        return ""
    }
}
