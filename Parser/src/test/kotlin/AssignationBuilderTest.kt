import astBuilders.AssignationBuilder
import astBuilders.AstBuilder
import exceptions.SyntacticError
import exceptions.UnexpectedTokenException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token

class AssignationBuilderTest {
    private val assignationBuilder = AssignationBuilder()

    @Test
    fun testWithValidTokensShouldReturnTrue() {
        val tokens =
            listOf(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(0, 1)),
                Token(DataType.ASSIGNATION, "=", Pair(0, 2), Pair(0, 3)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 4), Pair(0, 5)),
            )
        assertTrue(assignationBuilder.isValid(tokens))
    }

    @Test
    fun testWithInvalidTokensShouldReturnFalse() {
        val tokens =
            listOf(
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(0, 1)),
                Token(DataType.ASSIGNATION, "=", Pair(0, 2), Pair(0, 3)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 4), Pair(0, 5)),
            )
        assertFalse(assignationBuilder.isValid(tokens))
    }

    @Test
    fun testVerifyStructureWithInvalidaAssignationShouldThrowException() {
        val tokens =
            listOf(
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(0, 1)),
                Token(DataType.ASSIGNATION, "=", Pair(0, 2), Pair(0, 3)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 4), Pair(0, 5)),
            )
        assertThrows<UnexpectedTokenException> {
            assignationBuilder.build(tokens)
        }
    }

    @Test
    fun testForSemicolonShouldCorrectlyRemoveIt() {
        val tokens =
            listOf(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(0, 1)),
                Token(DataType.SEMICOLON, ";", Pair(0, 2), Pair(0, 3)),
            )
        val result = AstBuilder.takeCommentsAndSemiColon(tokens)
        assertEquals(1, result.size)
        assertEquals(DataType.VARIABLE_NAME, result[0].getType())
    }

    @Test
    fun testCheckMinLengthShouldThrowExceptionIfLessThanExpected() {
        val tokens =
            listOf(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(0, 1)),
            )
        assertThrows<SyntacticError> {
            AstBuilder.checkMinLength(tokens, 2, "declaration")
        }
    }

    @Test
    fun testCheckMaxLengthShouldThrowExceptionIfMoreThanExpected() {
        val tokens =
            listOf(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(0, 1)),
                Token(DataType.ASSIGNATION, "=", Pair(0, 2), Pair(0, 3)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 4), Pair(0, 5)),
            )
        assertThrows<SyntacticError> {
            AstBuilder.checkMaxLength(tokens, 2, "declaration")
        }
    }

    @Test
    fun testTokenTypeShouldThrowExceptionIfNotExpected() {
        val token = Token(DataType.NUMBER_VALUE, "5", Pair(0, 0), Pair(0, 1))
        assertThrows<UnexpectedTokenException> {
            AstBuilder.checkTokenType(token, "Variable", listOf(DataType.VARIABLE_NAME))
        }
    }
}
