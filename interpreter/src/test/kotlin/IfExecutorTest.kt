import astn.IfStatement
import astn.OperationBoolean
import astn.OperationVariable
import interpreter.Value
import interpreter.VariableType
import interpreter.executors.IfExecutor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import java.util.*

class IfExecutorTest {

    @Test
    fun test001ExecuteIfASTWithOperationVariableTrue(){
        val ifExecutor = IfExecutor()
        val ast = IfStatement(OperationVariable( Token( DataType.VARIABLE_NAME , "a" , Pair(0,0) , Pair(0,0) )))
        val variables = mutableMapOf("a" to Value( VariableType.BOOLEAN, Optional.of("true")))
        val result = ifExecutor.execute(ast, variables)
        assertEquals("true", result)
    }

    @Test
    fun test002ExecuteIfASTWithOperationVariableFalse(){
        val ifExecutor = IfExecutor()
        val ast = IfStatement(OperationVariable( Token( DataType.VARIABLE_NAME , "a" , Pair(0,0) , Pair(0,0) )))
        val variables = mutableMapOf("a" to Value( VariableType.BOOLEAN, Optional.of("false")))
        val result = ifExecutor.execute(ast, variables)
        assertEquals("false", result)
    }

    @Test
    fun test003ExecuteIfASTWithOperationVariableNotFound(){
        val ifExecutor = IfExecutor()
        val ast = IfStatement(OperationVariable( Token( DataType.VARIABLE_NAME , "a" , Pair(0,0) , Pair(0,0) )))
        val variables = mutableMapOf("b" to Value( VariableType.BOOLEAN, Optional.of("false")))
        val exception = assertThrows<Exception> {
            ifExecutor.execute(ast, variables)
        }
        assertEquals("Variable a not found at 0 : 0", exception.message)
    }

    @Test
    fun test004ExecuteIfASTWithOperationVariableNotBoolean(){
        val ifExecutor = IfExecutor()
        val ast = IfStatement(OperationVariable( Token( DataType.VARIABLE_NAME , "a" , Pair(0,0) , Pair(0,0) )))
        val variables = mutableMapOf("a" to Value( VariableType.STRING, Optional.of("false")))
        val exception = assertThrows<Exception> {
            ifExecutor.execute(ast, variables)
        }
        assertEquals("Variable a is not a boolean at 0 : 0", exception.message)
    }

    @Test
    fun test005ExecuteIfASTWithOperationBooleanTrue(){
        val ifExecutor = IfExecutor()
        val ast = IfStatement(OperationBoolean( Token( DataType.BOOLEAN_VALUE , "true" , Pair(0,0) , Pair(0,0) )))
        val variables = mutableMapOf<String , Value>()
        val result = ifExecutor.execute(ast, variables)
        assertEquals("true", result)
    }

    @Test
    fun test006ExecuteIfASTWithOperationBooleanFalse(){
        val ifExecutor = IfExecutor()
        val ast = IfStatement(OperationBoolean( Token( DataType.BOOLEAN_VALUE , "false" , Pair(0,0) , Pair(0,0) )))
        val variables = mutableMapOf<String , Value>()
        val result = ifExecutor.execute(ast, variables)
        assertEquals("false", result)
    }

    @Test
    fun test007AddCondition(){
        val ifExecutor = IfExecutor()
        val conditions = mutableListOf(true)
        val result = ifExecutor.addCondition(conditions, false)
        assertEquals(listOf(true, false), result)
    }
}