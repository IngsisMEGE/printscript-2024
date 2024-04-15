import astBuilders.CloseBuilder
import org.junit.jupiter.api.Test
import token.DataType
import token.Token

class CloseBuilderTest {
    @Test
    fun test001CloseBuildCorrectly() {
        val builder = CloseBuilder()
        val tokens =
            listOf(
                Token(DataType.RIGHT_BRACKET, "}", Pair(0, 1), Pair(0, 1)),
            )

        val ast = builder.build(tokens)

        assert(ast is astn.CloseIfStatement)
    }

    @Test
    fun test002IsValidShouldReturnTrue() {
        val builder = CloseBuilder()
        val tokens =
            listOf(
                Token(DataType.RIGHT_BRACKET, "}", Pair(0, 1), Pair(0, 1)),
            )

        val isValid = builder.isValid(tokens)

        assert(isValid)
    }

    @Test
    fun test003IsValidShouldReturnFalse() {
        val builder = CloseBuilder()
        val tokens =
            listOf(
                Token(DataType.RIGHT_BRACKET, "}", Pair(0, 1), Pair(0, 1)),
                Token(DataType.RIGHT_BRACKET, "}", Pair(1, 2), Pair(1, 2)),
            )
        val isValid = builder.isValid(tokens)

        assert(!isValid)
    }

    @Test
    fun test004IsValidShouldReturnFalse() {
        val builder = CloseBuilder()
        val tokens =
            listOf(
                Token(DataType.LEFT_BRACKET, "{", Pair(0, 1), Pair(0, 1)),
            )

        val isValid = builder.isValid(tokens)

        assert(!isValid)
    }

    @Test
    fun test005IsValidShouldReturnFalse() {
        val builder = CloseBuilder()
        val tokens =
            listOf(
                Token(DataType.RIGHT_BRACKET, "}", Pair(0, 1), Pair(0, 1)),
                Token(DataType.LEFT_BRACKET, "{", Pair(1, 2), Pair(1, 2)),
            )

        val isValid = builder.isValid(tokens)

        assert(!isValid)
    }
}
