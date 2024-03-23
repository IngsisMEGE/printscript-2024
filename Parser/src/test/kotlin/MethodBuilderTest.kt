import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import Parser.ASTBuilders.MethodBuilder
import ASTN.Method
import token.DataType
import token.Token

class MethodBuilderTest {

    private val methodBuilder = MethodBuilder()

    @Test
    fun testBuildWithValidTokens() {
        val tokens = listOf(
            Token(DataType.METHOD_CALL, "print", Pair(0,0), Pair(0,5)),
            Token(DataType.LEFT_PARENTHESIS, "", Pair(0,6), Pair(0,7)),
            Token(DataType.STRING_VALUE, "Hello, World!", Pair(0,8), Pair(0,21)),
            Token(DataType.RIGHT_PARENTHESIS, "", Pair(0,22), Pair(0,23))
        )
        val result = methodBuilder.build(tokens) as Method
        println(result.methodName.getValue())

        assertEquals("print", result.methodName.getValue())
    }
}