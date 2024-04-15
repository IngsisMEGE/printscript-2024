import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token

class VarDeclarationRuleTest {
    @Test
    fun test001IsRuleIncluded() {
        // Arrange
        val property: MutableMap<String, Any> = HashMap()
        property["SpaceInFront"] = 1
        property["SpaceInBack"] = 1
        val varDeclarationRule = rules.VarDeclarationRule("SpaceInFront", "SpaceInBack")

        // Act
        val result = varDeclarationRule.isTheRuleIncluded(property)

        // Assert
        assert(result is rules.VarDeclarationRule)
    }

    @Test
    fun test002SpaceInFrontIsNegativeShouldThrowException() {
        // Arrange
        val property: MutableMap<String, Any> = HashMap()
        property["SpaceInFront"] = -1
        property["SpaceInBack"] = 1
        val varDeclarationRule = rules.VarDeclarationRule("SpaceInFront", "SpaceInBack")

        // Act
        val exception =
            assertThrows<IllegalArgumentException> {
                varDeclarationRule.isTheRuleIncluded(property)
            }

        // Assert
        assertEquals("The amount of space in front must be greater than or equal to 0 for \":\" amount = -1", exception.message)
    }

    @Test
    fun test003GenericLine() {
        // Arrange
        val ast =
            astn.VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val varDeclarationRule = rules.VarDeclarationRule("SpaceInFront", "SpaceInBack")

        // Act
        val result = varDeclarationRule.genericLine(ast)

        // Assert
        assertEquals("let x:number", result)
    }

    @Test
    fun test004EnfoceRule() {
        val ast =
            astn.VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val property: MutableMap<String, Any> = HashMap()
        property["SpaceInFront"] = 1
        property["SpaceInBack"] = 1
        val varDeclarationRule = rules.VarDeclarationRule("SpaceInFront", "SpaceInBack")

        // Act
        val result = varDeclarationRule.isTheRuleIncluded(property)

        val code = result.genericLine(ast)

        // Assert
        assertEquals("let x : number;", result.enforceRule(code))
    }
}
