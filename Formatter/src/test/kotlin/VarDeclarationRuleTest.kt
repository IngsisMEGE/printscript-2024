import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token

class VarDeclarationRuleTest {

    @Test
    fun test001IsRuleIncluded() {
        // Arrange
        val property : MutableMap<String, Any> = HashMap()
        property["SpaceInFront"] = 1
        property["SpaceInBack"] = 1
        val varDeclarationRule = Rules.VarDeclarationRule("SpaceInFront" , "SpaceInBack")

        // Act
        val result = varDeclarationRule.isTheRuleIncluded(property)

        // Assert
        assert(result is Rules.VarDeclarationRule)
    }

    @Test
    fun test002SpaceInFrontIsNegativeShouldThrowException() {
        // Arrange
        val property : MutableMap<String, Any> = HashMap()
        property["SpaceInFront"] = -1
        property["SpaceInBack"] = 1
        val varDeclarationRule = Rules.VarDeclarationRule("SpaceInFront" , "SpaceInBack")

        // Act
        val exception = assertThrows<IllegalArgumentException> {
            varDeclarationRule.isTheRuleIncluded(property)
        }

        // Assert
        assertEquals("The ammount of space in front and back must be greater than 0 : Double Dot", exception.message)
    }

    @Test
    fun test003GenericLine() {
        // Arrange
        val ast = ASTN.VarDeclaration(Token(DataType.NUMBER_TYPE, "number", Pair(4,0) , Pair(5,0)), Token(DataType.VARIABLE_NAME, "x", Pair(0,0), Pair(1,0)))
        val varDeclarationRule = Rules.VarDeclarationRule("SpaceInFront" , "SpaceInBack")

        // Act
        val result = varDeclarationRule.genericLine(ast)

        // Assert
        assertEquals("let x:number;", result)
    }

    @Test
    fun test004EnfoceRule() {
        val ast = ASTN.VarDeclaration(Token(DataType.NUMBER_TYPE, "number", Pair(4,0) , Pair(5,0)), Token(DataType.VARIABLE_NAME, "x", Pair(0,0), Pair(1,0)))
        val property : MutableMap<String, Any> = HashMap()
        property["SpaceInFront"] = 1
        property["SpaceInBack"] = 1
        val varDeclarationRule = Rules.VarDeclarationRule("SpaceInFront" , "SpaceInBack")

        // Act
        val result = varDeclarationRule.isTheRuleIncluded(property)

        var code = result.genericLine(ast)

        // Assert
        assertEquals("let x : number;", result.enforceRule(code))
    }
}