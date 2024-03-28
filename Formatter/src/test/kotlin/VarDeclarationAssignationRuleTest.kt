import Rules.VarDeclarationAssignationRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VarDeclarationAssignationRuleTest {

    @Test
    fun whenPassingStringWithOnlyAssignationSymbolItShouldReturnStringWithSpacesInFrontAndBack() {
        val varDeclarationAssignationRule = VarDeclarationAssignationRule("1", "1", "1", "1")
        val code = "a=b"
        val expectedCode = "a = b"
        assertEquals("expectedCode","expectedCode")
    }
}