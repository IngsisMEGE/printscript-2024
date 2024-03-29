import ASTN.OperationString
import ASTN.VarDeclaration
import ASTN.VarDeclarationAssignation
import Enforcers.DoubleDotDeclarationEnforcer
import Rules.Rules
import Rules.VarDeclarationAssignationRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.DefaultAsserter.assertTrue

class VarDeclarationAssignationRuleTest {

    @Test
    fun whenPassingStringWithOnlyAssignationSymbolItShouldReturnStringWithSpacesInFrontAndBack() {
        var varDeclarationAssignationRule: Rules =
            VarDeclarationAssignationRule("DotFront", "DotBack", "EqualFront", "EqualBack")
        val map: MutableMap<String, Any> = HashMap()
        map["DotFront"] = 1
        map["DotBack"] = 1
        map["EqualFront"] = 1
        map["EqualBack"] = 1
        varDeclarationAssignationRule = varDeclarationAssignationRule.isTheRuleIncluded(map)
        val code = "a=b"
        val expectedCode = "a = b"
        assertEquals(expectedCode, varDeclarationAssignationRule.enforceRule(code))
    }

    @Test
    fun genericLineWithASTWorkingFine() {
        var varDeclarationAssignationRule: Rules =
            VarDeclarationAssignationRule("DotFront", "DotBack", "EqualFront", "EqualBack")
        val map: MutableMap<String, Any> = HashMap()
        map["DotFront"] = 1
        map["DotBack"] = 1
        map["EqualFront"] = 1
        map["EqualBack"] = 1
        varDeclarationAssignationRule = varDeclarationAssignationRule.isTheRuleIncluded(map)
        val ast: VarDeclarationAssignation = VarDeclarationAssignation(
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(0, 0), Pair(4, 0)),
                Token(DataType.VARIABLE_NAME, "dong", Pair(5, 0), Pair(8, 0))
            ), OperationString(Token(DataType.STRING_VALUE, "Hola", Pair(12, 0), Pair(15, 0)))
        )
        val expectedCode = "let dong:number=\"Hola\";"
        assertEquals(expectedCode, varDeclarationAssignationRule.genericLine(ast))
    }

    @Test
    fun test003VarDeclarationRuleIsIncludedTestWithGenericLineTestEnforceRule() {
        var varDeclarationAssignationRule: Rules =
            VarDeclarationAssignationRule("DotFront", "DotBack", "EqualFront", "EqualBack")
        val map: MutableMap<String, Any> = HashMap()
        map["DotFront"] = 1
        map["DotBack"] = 1
        map["EqualFront"] = 1
        map["EqualBack"] = 1
        varDeclarationAssignationRule = varDeclarationAssignationRule.isTheRuleIncluded(map)
        val ast: VarDeclarationAssignation = VarDeclarationAssignation(
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(0, 0), Pair(4, 0)),
                Token(DataType.VARIABLE_NAME, "dong", Pair(5, 0), Pair(8, 0))
            ), OperationString(Token(DataType.STRING_VALUE, "Hola", Pair(12, 0), Pair(15, 0)))
        )
        val expectedCode = "let dong : number = \"Hola\";"
        assertEquals(
            expectedCode,
            varDeclarationAssignationRule.enforceRule(varDeclarationAssignationRule.genericLine(ast))
        )
    }

    @Test
    fun test004SpaceDotFrontIsLessThanZeroWhenisRuleIncludedShouldThrowException() {
        var varDeclarationAssignationRule: Rules =
            VarDeclarationAssignationRule("DotFront", "DotBack", "EqualFront", "EqualBack")
        val map: MutableMap<String, Any> = HashMap()
        map["DotFront"] = -1
        map["DotBack"] = 1
        map["EqualFront"] = 1
        map["EqualBack"] = 1
        val exception = assertThrows<IllegalArgumentException> {
            varDeclarationAssignationRule = varDeclarationAssignationRule.isTheRuleIncluded(map)
        }

        assertEquals("The ammount of space in front and back must be greater than 0 : Double Dot", exception.message)
    }

    @Test
    fun test005SpaceDotBackIsLessThanZeroWhenIsRuleIncludedShouldThrowException() {
        var varDeclarationAssignationRule: Rules =
            VarDeclarationAssignationRule("DotFront", "DotBack", "EqualFront", "EqualBack")
        val map: MutableMap<String, Any> = HashMap()
        map["DotFront"] = 1
        map["DotBack"] = -1
        map["EqualFront"] = 1
        map["EqualBack"] = 1
        val exception = assertThrows<IllegalArgumentException> {
            varDeclarationAssignationRule = varDeclarationAssignationRule.isTheRuleIncluded(map)
        }

        assertEquals("The ammount of space in front and back must be greater than 0 : Double Dot", exception.message)
    }

    @Test
    fun test006SpaceEqualFrontIsLessThanZeroWhenIsRuleIncludedShouldThrowException() {
        var varDeclarationAssignationRule: Rules =
            VarDeclarationAssignationRule("DotFront", "DotBack", "EqualFront", "EqualBack")
        val map: MutableMap<String, Any> = HashMap()
        map["DotFront"] = 1
        map["DotBack"] = 1
        map["EqualFront"] = -1
        map["EqualBack"] = 1
        val exception = assertThrows<IllegalArgumentException> {
            varDeclarationAssignationRule = varDeclarationAssignationRule.isTheRuleIncluded(map)
        }

        assertEquals("The ammount of space in front and back must be greater than 0 : Assignation", exception.message)
    }

    @Test
    fun test007SpaceEqualBackIsLessThanZeroWhenIsRuleIncludedShouldThrowException() {
        var varDeclarationAssignationRule: Rules =
            VarDeclarationAssignationRule("DotFront", "DotBack", "EqualFront", "EqualBack")
        val map: MutableMap<String, Any> = HashMap()
        map["DotFront"] = 1
        map["DotBack"] = 1
        map["EqualFront"] = 1
        map["EqualBack"] = -1
        val exception = assertThrows<IllegalArgumentException> {
            varDeclarationAssignationRule = varDeclarationAssignationRule.isTheRuleIncluded(map)
        }

        assertEquals("The ammount of space in front and back must be greater than 0 : Assignation", exception.message)
    }


}