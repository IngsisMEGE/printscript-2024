import ASTN.OperationString
import Interpreter.Executors.AssignationExecution
import Interpreter.Value
import Interpreter.VariableType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.Test

class AssignationExecutionTest {

//    @Test
//    fun test001AssignationExecutorVariableNotFound() {
//        val assignationExecutor = AssignationExecution()
//        val ast = ASTN.Assignation(
//            Token(DataType.VARIABLE_NAME, "IAMNOTAVARIABLE", Pair(0, 0), Pair(6, 0)),
//            OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0)))
//        )
//        val exception = assertThrows<Exception> {
//            assignationExecutor.execute(ast, mutableMapOf())
//        }
//        assertEquals("Variable not found", exception.message)
//    }


}