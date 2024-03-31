import enforcers.OperatorSpaceEnforcer
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class OperatorSpaceEnforcerTest {
    @Test
    fun test001EnforceRule() {
        val operatorSpaceEnforcer = OperatorSpaceEnforcer()
        val code = "a+b"
        val result = operatorSpaceEnforcer.enforceRule(code)
        assertEquals("a + b", result)
    }

    @Test
    fun test002EnforceRule() {
        val operatorSpaceEnforcer = OperatorSpaceEnforcer()
        val code = "a-b"
        val result = operatorSpaceEnforcer.enforceRule(code)
        assertEquals("a - b", result)
    }

    @Test
    fun test003EnforceRule() {
        val operatorSpaceEnforcer = OperatorSpaceEnforcer()
        val code = "a*b"
        val result = operatorSpaceEnforcer.enforceRule(code)
        assertEquals("a * b", result)
    }

    @Test
    fun test004EnforceRule() {
        val operatorSpaceEnforcer = OperatorSpaceEnforcer()
        val code = "a/b"
        val result = operatorSpaceEnforcer.enforceRule(code)
        assertEquals("a / b", result)
    }

    @Test
    fun test005EnforceRule() {
        val operatorSpaceEnforcer = OperatorSpaceEnforcer()
        val code = "a%b"
        val result = operatorSpaceEnforcer.enforceRule(code)
        assertEquals("a % b", result)
    }

    @Test
    fun test006EnforceLongLineWithParenthesis() {
        val operatorSpaceEnforcer = OperatorSpaceEnforcer()
        val code = "(a+b)*c-d"
        val result = operatorSpaceEnforcer.enforceRule(code)
        assertEquals("(a + b) * c - d", result)
    }
}
