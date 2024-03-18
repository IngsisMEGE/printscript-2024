package token
import lexer.ListTokenManager
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

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
    @Test
    fun orderTokenTest() {
        val token1 = Token(DataType.SEMICOLON, "a", Pair(3, 3), Pair(1, 4))
        val token2 = Token(DataType.SEMICOLON, "b", Pair(0, 0), Pair(0, 0))
        val token3 = Token(DataType.SEMICOLON, "c", Pair(1, 4), Pair(1, 5))
        val token4 = Token(DataType.SEMICOLON, "d", Pair(1, 1), Pair(7, 1))
        val token5 = Token(DataType.SEMICOLON, "e", Pair(1, 2), Pair(3, 2))
        val tokenList = listOf(
            token1,
            token2,
            token3,
            token4,
            token5
        )
        val orderedList = ListTokenManager.orderTokens(tokenList)

        assertEquals(orderedList[0], token2)
        assertEquals(orderedList[1], token4)
        assertEquals(orderedList[2], token5)
        assertEquals(orderedList[3], token3)
        assertEquals(orderedList[4], token1)

    }


}