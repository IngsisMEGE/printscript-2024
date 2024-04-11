import enforcers.SpaceForCharacterEnforcer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SpaceCharacterEnforcerTest {
    @Test
    fun whenPassingStringWithOnlyAssignationSymbolItShouldReturnStringWithSpacesInFrontAndBack() {
        val assignationSpaceEnforcer = SpaceForCharacterEnforcer("=".first(), 1, 1)
        val code = "a=b"
        val expectedCode = "a = b"
        assertEquals(expectedCode, assignationSpaceEnforcer.enforceRule(code))
    }

    @Test
    fun whenAssigntationIsInsideStringItShouldNotAddSpaces() {
        val assignationSpaceEnforcer = SpaceForCharacterEnforcer("=".first(), 1, 1)
        val code = "a=\"b=c\""
        val expectedCode = "a = \"b=c\""
        assertEquals(expectedCode, assignationSpaceEnforcer.enforceRule(code))
    }

    @Test
    fun whenPassingStringWithOnlyDoubleDotItShouldReturnStringWithSpacesInFrontAndBack() {
        val doubleDotDeclarationEnforcer = SpaceForCharacterEnforcer(":".first(), 1, 1)
        val code = "a:b"
        val expectedCode = "a : b"
        assertEquals(expectedCode, doubleDotDeclarationEnforcer.enforceRule(code))
    }

    @Test
    fun whenDoubleDotIsInsideStringItShouldNotAddSpaces() {
        val doubleDotDeclarationEnforcer = SpaceForCharacterEnforcer(":".first(), 1, 1)
        val code = "a:\":b:c\""
        val expectedCode = "a : \":b:c\""
        assertEquals(expectedCode, doubleDotDeclarationEnforcer.enforceRule(code))
    }

    @Test
    fun test001EnforceRule() {
        val operatorSpaceEnforcer = SpaceForCharacterEnforcer("+".first(), 1, 1)
        val code = "a+b"
        val result = operatorSpaceEnforcer.enforceRule(code)
        assertEquals("a + b", result)
    }

    @Test
    fun test002EnforceRule() {
        val operatorSpaceEnforcer = SpaceForCharacterEnforcer("-".first(), 1, 1)
        val code = "a-b"
        val result = operatorSpaceEnforcer.enforceRule(code)
        assertEquals("a - b", result)
    }

    @Test
    fun test003EnforceRule() {
        val operatorSpaceEnforcer = SpaceForCharacterEnforcer("*".first(), 1, 1)
        val code = "a*b"
        val result = operatorSpaceEnforcer.enforceRule(code)
        assertEquals("a * b", result)
    }

    @Test
    fun test004EnforceRule() {
        val operatorSpaceEnforcer = SpaceForCharacterEnforcer("/".first(), 1, 1)
        val code = "a/b"
        val result = operatorSpaceEnforcer.enforceRule(code)
        assertEquals("a / b", result)
    }

    @Test
    fun test005EnforceRule() {
        val operatorSpaceEnforcer = SpaceForCharacterEnforcer("%".first(), 1, 1)
        val code = "a%b"
        val result = operatorSpaceEnforcer.enforceRule(code)
        assertEquals("a % b", result)
    }

    @Test
    fun test006EnforceLongLineWithParenthesis() {
        val plusSpaceEnforcer = SpaceForCharacterEnforcer("+".first(), 1, 1)
        val minusSpaceEnforcer = SpaceForCharacterEnforcer("-".first(), 1, 1)
        val multiplySpaceEnforcer = SpaceForCharacterEnforcer("*".first(), 1, 1)

        val code = "(a+b)*c-d"
        val result = plusSpaceEnforcer.enforceRule(code)
        val result2 = multiplySpaceEnforcer.enforceRule(result)
        val result3 = minusSpaceEnforcer.enforceRule(result2)
        assertEquals("(a + b) * c - d", result3)
    }
}
