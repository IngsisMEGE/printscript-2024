import astBuilders.DeclaratorBuilder
import astn.VarDeclaration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.DataType
import token.Token

class DeclarationBuilderTest {
    private val declaratorBuilder = DeclaratorBuilder()

    @Test
    fun testWithValidTokensShouldCorrectlyBuildVarDeclaration() {
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(0, 0), Pair(0, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 4), Pair(0, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(0, 6), Pair(0, 7)),
                Token(DataType.NUMBER_TYPE, "", Pair(0, 8), Pair(0, 14)),
            )
        val result = declaratorBuilder.build(tokens) as VarDeclaration
        assertEquals("x", result.varName.getValue())
        assertEquals("", result.type.getValue())
    }
}
