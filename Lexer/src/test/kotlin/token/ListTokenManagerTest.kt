package token

import org.example.lexer.ListTokenManager
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class ListTokenManagerTest {
    @Test
    fun orderTokenTest() {
        val token1 = Token(DataType.SEMICOLON, "a", Pair(3, 3), Pair(1, 4))
        val token2 = Token(DataType.SEMICOLON, "b", Pair(0, 0), Pair(0, 0))
        val token3 = Token(DataType.SEMICOLON, "c", Pair(1, 4), Pair(1, 5))
        val token4 = Token(DataType.SEMICOLON, "d", Pair(1, 1), Pair(7, 1))
        val token5 = Token(DataType.SEMICOLON, "e", Pair(1, 2), Pair(3, 2))
        val tokenList =
            listOf(
                token1,
                token2,
                token3,
                token4,
                token5,
            )
        val orderedList = ListTokenManager.orderTokens(tokenList)
        assertEquals(orderedList[0], token2)
        assertEquals(orderedList[1], token4)
        assertEquals(orderedList[2], token5)
        assertEquals(orderedList[3], token3)
        assertEquals(orderedList[4], token1)
    }

    @Test
    fun testRemoveOverlapingTokens() {
        val token1 = Token(DataType.SEMICOLON, "a", Pair(3, 3), Pair(1, 4))
        val token2 = Token(DataType.SEMICOLON, "b", Pair(0, 0), Pair(0, 0))
        val token3 = Token(DataType.SEMICOLON, "c", Pair(1, 4), Pair(1, 5))
        val token4 = Token(DataType.SEMICOLON, "d", Pair(1, 1), Pair(7, 1))
        val token5 = Token(DataType.SEMICOLON, "e", Pair(1, 2), Pair(3, 2))
        val token6 = Token(DataType.SEMICOLON, "e", Pair(1, 2), Pair(3, 2))
        val tokenList =
            listOf(
                token1,
                token2,
                token3,
                token4,
                token5,
                token6,
            )
        val orderedList = ListTokenManager.removeOverlapTokens(tokenList)
        assertEquals(orderedList.size, 2)
    }
}
