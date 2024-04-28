import astBuilders.ElseBuilder
import exceptions.UnexpectedTokenException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token

class ElseBuilderTest {
    @Test
    fun test001ElseBuildCorrectly() {
        val builder = ElseBuilder()
        val tokens =
            listOf(
                Token(DataType.ELSE_STATEMENT, "else", Pair(0, 1), Pair(0, 1)),
                Token(DataType.LEFT_BRACKET, "{", Pair(1, 2), Pair(1, 2)),
            )

        val ast = builder.build(tokens)

        assert(ast is astn.CloseIfStatement)

        val closeIfStatement = ast as astn.CloseIfStatement
        assert(closeIfStatement.isElse)
    }

    @Test
    fun test002IsValidShouldReturnFalse() {
        val builder = ElseBuilder()
        val tokens =
            listOf(
                Token(DataType.ELSE_STATEMENT, "else", Pair(0, 1), Pair(0, 1)),
                Token(DataType.LEFT_BRACKET, "{", Pair(1, 2), Pair(1, 2)),
            )

        val isValid = builder.isValid(tokens)

        assert(!isValid)
    }

    @Test
    fun test003IsValidShouldReturnTrue() {
        val builder = ElseBuilder()
        val tokens =
            listOf(
                Token(DataType.ELSE_STATEMENT, "else", Pair(0, 1), Pair(0, 1)),
            )

        val isValid = builder.isValid(tokens)

        assert(isValid)
    }

    @Test
    fun test004IsValidShouldReturnFalse() {
        val builder = ElseBuilder()
        val tokens =
            listOf(
                Token(DataType.ELSE_STATEMENT, "else", Pair(0, 1), Pair(0, 1)),
                Token(DataType.LEFT_BRACKET, "{", Pair(1, 2), Pair(1, 2)),
                Token(DataType.RIGHT_BRACKET, "}", Pair(2, 3), Pair(2, 3)),
            )

        val isValid = builder.isValid(tokens)

        assert(!isValid)
    }

    @Test
    fun test005BuildWithInvalidTokensShouldThrowException() {
        val builder = ElseBuilder()
        val tokens =
            listOf(
                Token(DataType.IF_STATEMENT, "if", Pair(0, 1), Pair(0, 1)),
                Token(DataType.LEFT_BRACKET, "{", Pair(1, 2), Pair(1, 2)),
                Token(DataType.RIGHT_BRACKET, "}", Pair(2, 3), Pair(2, 3)),
            )

        assertThrows<UnexpectedTokenException> {
            builder.build(tokens)
        }
    }
}
