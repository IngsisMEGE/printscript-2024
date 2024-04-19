import astBuilders.IfBuilder
import astn.OperationBoolean
import astn.OperationVariable
import exceptions.SyntacticError
import exceptions.UnexpectedTokenException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token

class IfBuilderTest {
    @Test
    fun test001IfStatementBuildCorrectlyWithBooleanValue() {
        // Given
        val tokens =
            listOf(
                Token(DataType.IF_STATEMENT, "if", Pair(0, 0), Pair(2, 0)),
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(0, 0), Pair(2, 0)),
                Token(DataType.BOOLEAN_VALUE, "true", Pair(0, 0), Pair(2, 0)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(0, 0), Pair(2, 0)),
                Token(DataType.LEFT_BRACKET, "{", Pair(0, 0), Pair(2, 0)),
            )

        val ifBuilder = IfBuilder()

        // When
        val ast = ifBuilder.build(tokens) as astn.IfStatement

        val op = ast.condition as OperationBoolean
        // Then
        assertEquals(DataType.BOOLEAN_VALUE, op.value.getType())
    }

    @Test
    fun test002IfStatementBuildCorrectlyWithVariable() {
        // Given
        val tokens =
            listOf(
                Token(DataType.IF_STATEMENT, "if", Pair(0, 0), Pair(2, 0)),
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(0, 0), Pair(2, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(2, 0)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(0, 0), Pair(2, 0)),
                Token(DataType.LEFT_BRACKET, "{", Pair(0, 0), Pair(2, 0)),
            )

        val ifBuilder = IfBuilder()

        // When
        val ast = ifBuilder.build(tokens) as astn.IfStatement

        val op = ast.condition as OperationVariable
        // Then
        assertEquals(DataType.VARIABLE_NAME, op.varName.getType())
    }

    @Test
    fun test003IfStatementWithoutBracesShouldThrowException() {
        // Given
        val tokens =
            listOf(
                Token(DataType.IF_STATEMENT, "if", Pair(0, 0), Pair(2, 0)),
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(0, 0), Pair(2, 0)),
                Token(DataType.BOOLEAN_VALUE, "true", Pair(0, 0), Pair(2, 0)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(0, 0), Pair(2, 0)),
                Token(DataType.SEPARATOR, ";", Pair(0, 0), Pair(2, 0)),
            )

        val ifBuilder = IfBuilder()

        assertThrows<UnexpectedTokenException> {
            ifBuilder.build(tokens)
        }
    }

    @Test
    fun test004IfStatementWithoutConditionShouldThrowException() {
        // Given
        val tokens =
            listOf(
                Token(DataType.IF_STATEMENT, "if", Pair(0, 0), Pair(2, 0)),
                Token(DataType.LEFT_BRACKET, "{", Pair(0, 0), Pair(2, 0)),
            )

        val ifBuilder = IfBuilder()

        assertThrows<SyntacticError> {
            ifBuilder.build(tokens)
        }
    }
}
