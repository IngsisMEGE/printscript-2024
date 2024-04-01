import enforcers.DoubleDotDeclarationEnforcer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DoubleDotEnforcerTest {
    @Test
    fun whenPassingStringWithOnlyDoubleDotItShouldReturnStringWithSpacesInFrontAndBack() {
        val doubleDotDeclarationEnforcer = DoubleDotDeclarationEnforcer(1, 1)
        val code = "a:b"
        val expectedCode = "a : b"
        assertEquals(expectedCode, doubleDotDeclarationEnforcer.enforceRule(code))
    }

    @Test
    fun whenDoubleDotIsInsideStringItShouldNotAddSpaces() {
        val doubleDotDeclarationEnforcer = DoubleDotDeclarationEnforcer(1, 1)
        val code = "a:\":b:c\""
        val expectedCode = "a : \":b:c\""
        assertEquals(expectedCode, doubleDotDeclarationEnforcer.enforceRule(code))
    }
}
