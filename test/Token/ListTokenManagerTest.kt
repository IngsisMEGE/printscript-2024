package Token

import lexer.ListTokenManager
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import token.DataType
import token.Token

class ListTokenManagerTest {
    @Test
    fun OrderTokenTest() {
        val token1 = Token(DataType.SEMICOLON, "a", Pair(2, 3), Pair(1, 4))
        val token2 = Token(DataType.SEMICOLON, "b", Pair(0, 0), Pair(0, 0))
        val token3 = Token(DataType.SEMICOLON, "c", Pair(1, 3), Pair(1, 3))
        val token4 = Token(DataType.SEMICOLON, "d", Pair(1, 3), Pair(1, 2))
        val token5 = Token(DataType.SEMICOLON, "e", Pair(1, 3), Pair(1, 1))
        val tokenList = listOf(
            token1,
            token2,
            token3,
            token4,
            token5
        )
        val orderedList = ListTokenManager.orderTokens(tokenList)

        assertEquals(orderedList[0], token2)
        assertEquals(orderedList[1], token5)
        assertEquals(orderedList[2], token4)
        assertEquals(orderedList[3], token3)
        assertEquals(orderedList[4], token1)
    }
}