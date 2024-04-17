import enforcers.IndentedIfElseBlockEnforcer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IndentedIfElseBlockEnforcerTest {
    @Test
    fun test001whenPassingLineItAdds4Indentation() {
        val indentedIfElseBlockEnforcer = IndentedIfElseBlockEnforcer(4)
        val code = "val x = 1"
        val expectedCode = "    val x = 1"
        assertEquals(expectedCode, indentedIfElseBlockEnforcer.enforceRule(code, 1))
    }

    @Test
    fun test002whenPassingLineItAdds0Indentation() {
        val indentedIfElseBlockEnforcer = IndentedIfElseBlockEnforcer(0)
        val code = "val x = 1"
        val expectedCode = "val x = 1"
        assertEquals(expectedCode, indentedIfElseBlockEnforcer.enforceRule(code))
    }
}
