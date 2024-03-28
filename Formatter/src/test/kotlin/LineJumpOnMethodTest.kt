import Enforcers.LineJumpOnMethodEnforcer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LineJumpOnMethodTest {

    @Test
    fun whenPassingMethodNameItAddsXLineJumps() {
        val lineJumpOnMethodEnforcer = LineJumpOnMethodEnforcer(1)
        val code = "fun test() {"
        val expectedCode = "\nfun test() {"
        assertEquals(expectedCode, lineJumpOnMethodEnforcer.enforceRule(code))

    }
}