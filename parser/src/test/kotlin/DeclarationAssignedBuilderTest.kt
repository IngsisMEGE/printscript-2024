import astBuilders.DeclarationAssignedBuilder
import astn.OperationString
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import exceptions.MustEndWithSeparator
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.assertEquals

class DeclarationAssignedBuilderTest {
    private val declarationAssignedBuilder = DeclarationAssignedBuilder()

    @Test
    fun testWithValidTokensShouldReturnTrue() {
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(0, 0), Pair(0, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 4), Pair(0, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(0, 6), Pair(0, 7)),
                Token(DataType.NUMBER_TYPE, "number", Pair(0, 8), Pair(0, 14)),
                Token(DataType.ASSIGNATION, "=", Pair(0, 15), Pair(0, 16)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 17), Pair(0, 18)),
            )
        assertTrue(declarationAssignedBuilder.isValid(tokens))
    }

    @Test
    fun testWithInvalidTokensShouldReturnFalse() {
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(0, 0), Pair(0, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 4), Pair(0, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(0, 6), Pair(0, 7)),
                Token(DataType.NUMBER_TYPE, "number", Pair(0, 8), Pair(0, 14)),
            )
        assertFalse(declarationAssignedBuilder.isValid(tokens))
    }

    @Test
    fun test003WithValidTokenSpaceInMiddleStringShouldReturnTrue() {
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(0, 0), Pair(0, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 4), Pair(0, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(0, 6), Pair(0, 7)),
                Token(DataType.STRING_TYPE, "string", Pair(0, 8), Pair(0, 14)),
                Token(DataType.ASSIGNATION, "=", Pair(0, 15), Pair(0, 16)),
                Token(DataType.STRING_VALUE, "\"Hello World\"", Pair(0, 17), Pair(0, 30)),
            )
        assertTrue(declarationAssignedBuilder.isValid(tokens))
    }

    @Test
    fun test004BuildASTWithStringSpaceInMiddleShouldReturnVarDeclarationAssignation() {
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(0, 0), Pair(0, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 4), Pair(0, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(0, 6), Pair(0, 7)),
                Token(DataType.STRING_TYPE, "string", Pair(0, 8), Pair(0, 14)),
                Token(DataType.ASSIGNATION, "=", Pair(0, 15), Pair(0, 16)),
                Token(DataType.STRING_VALUE, "\"Hello World\"", Pair(0, 17), Pair(0, 30)),
                Token(DataType.SEPARATOR, ";", Pair(0, 31), Pair(0, 32)),
            )
        assertTrue(declarationAssignedBuilder.isValid(tokens))
        val ast: VarDeclarationAssignation = declarationAssignedBuilder.build(tokens) as VarDeclarationAssignation
        val expected: VarDeclarationAssignation =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.STRING_TYPE, "string", Pair(0, 8), Pair(0, 14)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 4), Pair(0, 5)),
                    true,
                ),
                OperationString(Token(DataType.STRING_VALUE, "\"Hello World\"", Pair(0, 17), Pair(0, 30))),
            )

        val expectedOpString: OperationString = expected.value as OperationString
        val astOpString: OperationString = ast.value as OperationString

        assertEquals(expected.varDeclaration.varName.getValue(), ast.varDeclaration.varName.getValue())
        assertEquals(expected.varDeclaration.type.getValue(), ast.varDeclaration.type.getValue())
        assertEquals(expectedOpString.value.getValue(), astOpString.value.getValue())
    }

    @Test
    fun test005BuildWithValidImmutableTokensShouldReturnVarDeclarationAssignation() {
        val tokens =
            listOf(
                Token(DataType.DECLARATION_IMMUTABLE, "const", Pair(0, 0), Pair(0, 5)),
                Token(DataType.VARIABLE_NAME, "y", Pair(0, 6), Pair(0, 7)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(0, 8), Pair(0, 9)),
                Token(DataType.STRING_TYPE, "string", Pair(0, 10), Pair(0, 16)),
                Token(DataType.ASSIGNATION, "=", Pair(0, 17), Pair(0, 18)),
                Token(DataType.STRING_VALUE, "\"Hello World\"", Pair(0, 19), Pair(0, 32)),
                Token(DataType.SEPARATOR, ";", Pair(0, 32), Pair(0, 33)),
            )
        assertTrue(declarationAssignedBuilder.isValid(tokens))
        val ast: VarDeclarationAssignation = declarationAssignedBuilder.build(tokens) as VarDeclarationAssignation
        val expected: VarDeclarationAssignation =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.STRING_TYPE, "string", Pair(0, 10), Pair(0, 16)),
                    Token(DataType.VARIABLE_NAME, "y", Pair(0, 6), Pair(0, 7)),
                    false,
                ),
                OperationString(Token(DataType.STRING_VALUE, "\"Hello World\"", Pair(0, 19), Pair(0, 32))),
            )

        val expectedOpString: OperationString = expected.value as OperationString
        val astOpString: OperationString = ast.value as OperationString

        assertEquals(expected.varDeclaration.varName.getValue(), ast.varDeclaration.varName.getValue())
        assertEquals(expected.varDeclaration.type.getValue(), ast.varDeclaration.type.getValue())
        assertEquals(expectedOpString.value.getValue(), astOpString.value.getValue())
        assertEquals(expected.varDeclaration.isMutable, ast.varDeclaration.isMutable)
    }

    @Test
    fun test006MustEndWithSeparator() {
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(0, 0), Pair(0, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 4), Pair(0, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(0, 6), Pair(0, 7)),
                Token(DataType.NUMBER_TYPE, "number", Pair(0, 8), Pair(0, 14)),
                Token(DataType.ASSIGNATION, "=", Pair(0, 15), Pair(0, 16)),
                Token(DataType.NUMBER_VALUE, "5", Pair(0, 17), Pair(0, 18)),
            )
        assertThrows<MustEndWithSeparator> {
            declarationAssignedBuilder.build(tokens)
        }
    }
}
