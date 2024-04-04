
import astn.OperationHead
import astn.OperationNumber
import astn.OperationString
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import interpreter.Value
import interpreter.executors.DeclarationAssignationExecution
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.Test

class DeclarationAssignationExecutionTest {
    @Test
    fun test001DeclarationAssignationExecutorVariableNotFound() {
        val map = mutableMapOf<String, Value>()
        val declarationAssignationExecutor = DeclarationAssignationExecution()
        val ast1 =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                    Token(DataType.VARIABLE_NAME, "IALREADYEXIST", Pair(0, 0), Pair(6, 0)),
                ),
                OperationNumber(Token(DataType.NUMBER_TYPE, "4", Pair(7, 0), Pair(12, 0))),
            )
        val ast2 =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                    Token(DataType.VARIABLE_NAME, "IALREADYEXIST", Pair(0, 0), Pair(6, 0)),
                ),
                OperationNumber(Token(DataType.NUMBER_TYPE, "1", Pair(7, 0), Pair(12, 0))),
            )
        declarationAssignationExecutor.execute(ast1, map)
        val exception =
            assertThrows<Exception> {
                declarationAssignationExecutor.execute(ast2, map)
            }
        assertEquals("Variable Already Exists", exception.message)
    }

    @Test
    fun test002TypeMismatch() {
        val map = mutableMapOf<String, Value>()
        val declarationAssignationExecutor = DeclarationAssignationExecution()
        val ast1 =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                    Token(DataType.VARIABLE_NAME, "IALREADYEXIST", Pair(0, 0), Pair(6, 0)),
                ),
                OperationString(Token(DataType.STRING_TYPE, "4", Pair(7, 0), Pair(12, 0))),
            )
        val exception =
            assertThrows<Exception> {
                declarationAssignationExecutor.execute(ast1, map)
            }
        assertEquals("Type Mismatch", exception.message)
    }

    @Test
    fun test003DeclarationExecutionWorksFineTypeNumber() {
        val map = mutableMapOf<String, Value>()
        val declarationAssignationExecutor = DeclarationAssignationExecution()
        val ast =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                ),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0)),
                ),
            )
        val result = declarationAssignationExecutor.execute(ast, map)
        assertEquals("", result)
    }

    @Test
    fun test004DeclarationExecutionWorksFineTypeString() {
        val map = mutableMapOf<String, Value>()
        val declarationAssignationExecutor = DeclarationAssignationExecution()
        val ast =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.STRING_TYPE, "string", Pair(4, 0), Pair(5, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                ),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0)),
                ),
            )
        val result = declarationAssignationExecutor.execute(ast, map)
        assertEquals("", result)
    }

    @Test
    fun test005DeclarationExecutionWorksWithOperationsOfNumbers() {
        val map = mutableMapOf<String, Value>()
        val declarationAssignationExecutor = DeclarationAssignationExecution()
        val ast =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                ),
                OperationHead(
                    Token(DataType.OPERATOR_PLUS, "+", Pair(7, 0), Pair(12, 0)),
                    OperationNumber(Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0))),
                    OperationNumber(Token(DataType.NUMBER_VALUE, "2", Pair(7, 0), Pair(12, 0))),
                ),
            )
        val result = declarationAssignationExecutor.execute(ast, map)
        assertEquals("", result)
    }

    @Test
    fun test006OperationsWithStringShouldWork() {
        val map = mutableMapOf<String, Value>()
        val declarationAssignationExecutor = DeclarationAssignationExecution()
        val ast =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.STRING_TYPE, "string", Pair(4, 0), Pair(5, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                ),
                OperationHead(
                    Token(DataType.OPERATOR_PLUS, "+", Pair(7, 0), Pair(12, 0)),
                    OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))),
                    OperationString(Token(DataType.STRING_VALUE, "World", Pair(7, 0), Pair(12, 0))),
                ),
            )
        val result = declarationAssignationExecutor.execute(ast, map)
        assertEquals("", result)
    }

    @Test
    fun test007OperationWithStringAndNumberShouldWork() {
        val map = mutableMapOf<String, Value>()
        val declarationAssignationExecutor = DeclarationAssignationExecution()
        val ast =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.STRING_TYPE, "string", Pair(4, 0), Pair(5, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                ),
                OperationHead(
                    Token(DataType.OPERATOR_PLUS, "+", Pair(7, 0), Pair(12, 0)),
                    OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))),
                    OperationNumber(
                        Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0)),
                    ),
                ),
            )
        val result = declarationAssignationExecutor.execute(ast, map)
        assertEquals("", result)
    }
}