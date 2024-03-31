import enforcers.AssignationSpaceEnforcer
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class AssignationSpaceEnfocerTest {
    @Test
    fun whenPassingStringWithOnlyAssignationSymbolItShouldReturnStringWithSpacesInFrontAndBack() {
        val assignationSpaceEnforcer = AssignationSpaceEnforcer(1, 1)
        val code = "a=b"
        val expectedCode = "a = b"
        assertEquals(expectedCode, assignationSpaceEnforcer.enforceRule(code))
    }

    @Test
    fun whenAssigntationIsInsideStringItShouldNotAddSpaces() {
        val assignationSpaceEnforcer = AssignationSpaceEnforcer(1, 1)
        val code = "a=\"b=c\""
        val expectedCode = "a = \"b=c\""
        assertEquals(expectedCode, assignationSpaceEnforcer.enforceRule(code))
    }
}
