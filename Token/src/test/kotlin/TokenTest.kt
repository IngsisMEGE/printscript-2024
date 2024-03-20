import org.junit.jupiter.api.Assertions.assertEquals
import token.DataType
import token.Token
import kotlin.test.Test

class TokenTest {
    @Test
    fun createTokenTest() {
        val token = Token(DataType.SEMICOLON, "a", Pair(2, 3), Pair(1, 4))
        assertEquals(token.getType(), DataType.SEMICOLON)
        assertEquals(token.getValue(), "a")
        assertEquals(token.getInitialPosition(), Pair(2, 3))
        assertEquals(token.getFinalPosition(), Pair(1, 4))
    }

    @Test
    fun createTokenWithoutValueTest() {
        val token = Token(DataType.SEMICOLON, null, Pair(2, 3), Pair(1, 4))
        assertEquals(token.getType(), DataType.SEMICOLON)
        assertEquals(token.getValue(), "")
        assertEquals(token.getInitialPosition(), Pair(2, 3))
        assertEquals(token.getFinalPosition(), Pair(1, 4))
    }

    @Test
    fun createTokenWithInitialPositionGreaterThanFinalPositionTest() {
        try {
            Token(DataType.SEMICOLON, "a", Pair(2, 3), Pair(1, 3))
        } catch (e: Exception) {
            assertEquals(e.message, "The initial position must be less than the final position")
        }
    }

}