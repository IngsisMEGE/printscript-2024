import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import Parser.ASTBuilders.AssignationBuilder
import token.DataType
import token.Token

class AssignationBuilderTest {

    private val assignationBuilder = AssignationBuilder()

    @Test
    fun `test isValid with valid tokens`() {
        val tokens = listOf(
            Token(DataType.VARIABLE_NAME, "x", Pair(0,0), Pair(0,1)),
            Token(DataType.ASSIGNATION, "=", Pair(0,2), Pair(0,3)),
            Token(DataType.NUMBER_VALUE, "5", Pair(0,4), Pair(0,5))
        )
        assertTrue(assignationBuilder.isValid(tokens))
    }

    @Test
    fun `test isValid with invalid tokens`() {
        val tokens = listOf(
            Token(DataType.NUMBER_VALUE, "5", Pair(0,0), Pair(0,1)),
            Token(DataType.ASSIGNATION, "=", Pair(0,2), Pair(0,3)),
            Token(DataType.NUMBER_VALUE, "5", Pair(0,4), Pair(0,5))
        )
        assertFalse(assignationBuilder.isValid(tokens))
    }
}