import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import Parser.ASTBuilders.DeclarationAssignedBuilder
import token.DataType
import token.Token

class DeclarationAssignedBuilderTest {

    private val declarationAssignedBuilder = DeclarationAssignedBuilder()

    @Test
    fun `test isValid with valid tokens`() {
        val tokens = listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(0,0), Pair(0,3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0,4), Pair(0,5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(0,6), Pair(0,7)),
                Token(DataType.NUMBER_TYPE, "number", Pair(0,8), Pair(0,14)),
                Token(DataType.ASSIGNATION, "=", Pair(0,15), Pair(0,16)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0,17), Pair(0,18))
        )
        assertTrue(declarationAssignedBuilder.isValid(tokens))
    }

    @Test
    fun `test isValid with invalid tokens`() {
        val tokens = listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(0,0), Pair(0,3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0,4), Pair(0,5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(0,6), Pair(0,7)),
                Token(DataType.NUMBER_TYPE, "number", Pair(0,8), Pair(0,14))
        )
        assertFalse(declarationAssignedBuilder.isValid(tokens))
    }
}