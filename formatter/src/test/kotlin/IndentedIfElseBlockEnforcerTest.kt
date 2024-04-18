import astn.OperationVariable
import enforcers.IndentedIfElseBlockEnforcer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.DataType
import token.Token

class IndentedIfElseBlockEnforcerTest {
    @Test
    fun test001whenPassingLineItAdds4Indentation() {
        val indentedIfElseBlockEnforcer = IndentedIfElseBlockEnforcer(4)
        indentedIfElseBlockEnforcer.didEnterIf(
            astn.IfStatement(OperationVariable(Token(DataType.VARIABLE_NAME, "a", Pair(0, 0), Pair(0, 0)))),
        )
        val code = "val x = 1"
        val enforcedCode = indentedIfElseBlockEnforcer.enforceRule() + code
        val expectedCode = "    val x = 1"
        assertEquals(expectedCode, enforcedCode)
    }

    @Test
    fun test002whenPassingLineItAdds0Indentation() {
        val indentedIfElseBlockEnforcer = IndentedIfElseBlockEnforcer(0)
        indentedIfElseBlockEnforcer.didEnterIf(
            astn.IfStatement(OperationVariable(Token(DataType.VARIABLE_NAME, "a", Pair(0, 0), Pair(0, 0)))),
        )
        val code = "val x = 1"
        val enforcedCode = indentedIfElseBlockEnforcer.enforceRule() + code
        val expectedCode = "val x = 1"
        assertEquals(expectedCode, enforcedCode)
    }
}
