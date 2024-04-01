package token

import org.example.lexer.RegexTokenGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RegexTokenGeneratorTest {
    @Test
    fun testLiteralPatternTokenGeneration() {
        val generator = RegexTokenGenerator("\"(?:\\\\.|[^\"])*\"", DataType.STRING_VALUE, true)
        val line = "let name = \"Susan\";"
        val tokens = generator.generateToken(line, 1)

        assertEquals(1, tokens.size, "Should generate exactly one token")
        assertEquals("", tokens[0].getValue(), "Token value should be empty for literal patterns")
        assertEquals(DataType.STRING_VALUE, tokens[0].getType(), "Token type should be STRING_VALUE")
    }
}
