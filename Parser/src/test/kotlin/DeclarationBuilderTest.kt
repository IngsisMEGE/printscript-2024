import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import Parser.ASTBuilders.DeclaratorBuilder
import ASTN.VarDeclaration
import token.DataType
import token.Token

class DeclaratorBuilderTest {

    private val declaratorBuilder = DeclaratorBuilder()

    @Test
    fun `test build with valid tokens`() {
        val tokens = listOf(
            Token(DataType.LET_KEYWORD, "let", Pair(0,0), Pair(0,3)),
            Token(DataType.VARIABLE_NAME, "x", Pair(0,4), Pair(0,5)),
            Token(DataType.DOUBLE_DOTS, ":", Pair(0,6), Pair(0,7)),
            Token(DataType.NUMBER_KEYWORD, "", Pair(0,8), Pair(0,14))
        )
        val result = declaratorBuilder.build(tokens) as VarDeclaration
        assertEquals("x", result.assignation.getValue())
        assertEquals("", result.type.getValue())
    }
}