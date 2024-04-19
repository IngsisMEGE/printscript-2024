import astBuilders.DeclaratorBuilder
import astn.VarDeclaration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.DataType
import token.Token

class DeclarationBuilderTest {
    private val declaratorBuilder = DeclaratorBuilder(true)

    @Test
    fun testWithValidTokensShouldCorrectlyBuildVarDeclaration() {
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(0, 0), Pair(0, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 4), Pair(0, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(0, 6), Pair(0, 7)),
                Token(DataType.NUMBER_TYPE, "", Pair(0, 8), Pair(0, 14)),
                Token(DataType.SEPARATOR, ";", Pair(0, 15), Pair(0, 16)),
            )
        val result = declaratorBuilder.build(tokens) as VarDeclaration
        assertEquals("x", result.varName.getValue())
        assertEquals("", result.type.getValue())
        assertEquals(true, result.isMutable)
    }

    @Test
    fun test002_WithValidTokensShouldCorrectlyBuildImmutableVarDeclaration() {
        val tokens =
            listOf(
                Token(DataType.DECLARATION_IMMUTABLE, "const", Pair(0, 0), Pair(0, 5)),
                Token(DataType.VARIABLE_NAME, "y", Pair(0, 6), Pair(0, 7)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(0, 8), Pair(0, 9)),
                Token(DataType.STRING_TYPE, "", Pair(0, 10), Pair(0, 16)),
                Token(DataType.SEPARATOR, ";", Pair(0, 17), Pair(0, 18)),
            )
        val result = declaratorBuilder.build(tokens) as VarDeclaration
        assertEquals("y", result.assignation.getValue())
        assertEquals("", result.type.getValue())
        assertEquals(false, result.isMutable)
    }
}
