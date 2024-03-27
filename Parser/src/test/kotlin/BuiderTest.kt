import ASTN.AST
import ASTN.OpTree
import ASTN.VarDeclarationAssignation
import Parser.ASTBuilders.DeclarationAssignedBuilder
import Parser.ASTBuilders.DeclaratorBuilder
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import token.DataType
import token.Token
import kotlin.test.assertEquals

class BuiderTest {


    @Test
    fun isValidDeclarationAssignationShouldReturnFalseForLinesWithLessThan6Tokens() {
        val tokens = listOf(
            Token(DataType.DECLARATION_VARIABLE, "", Pair(0,0), Pair(3,0)),
            Token(DataType.VARIABLE_NAME, "x", Pair(4,0), Pair(5,0)),
            Token(DataType.DOUBLE_DOTS, "", Pair(6,0), Pair(7,0)),
            Token(DataType.STRING_TYPE, "", Pair(8,0), Pair(9,0)),
            Token(DataType.ASSIGNATION, "", Pair(10,0), Pair(11,0)),
        )

        val declaratorBuilder = DeclarationAssignedBuilder()
        assert(!declaratorBuilder.isValid(tokens))
    }
    @Test
    fun isValidDeclarationAssignationShouldReturnTrueForLinesWithLessThan6Tokens() {
        val tokens = listOf(
            Token(DataType.DECLARATION_VARIABLE, "", Pair(0,0), Pair(3,0)),
            Token(DataType.VARIABLE_NAME, "x", Pair(4,0), Pair(5,0)),
            Token(DataType.DOUBLE_DOTS, "", Pair(6,0), Pair(7,0)),
            Token(DataType.STRING_TYPE, "", Pair(8,0), Pair(9,0)),
            Token(DataType.ASSIGNATION, "", Pair(10,0), Pair(11,0)),
            Token(DataType.NUMBER_VALUE, "5", Pair(12,0), Pair(13,0))
        )
        val declaratorBuilder = DeclarationAssignedBuilder()
        assert(declaratorBuilder.isValid(tokens))
    }
    @Test
    fun buildDeclarationAssignationShouldReturnVarDeclarationAssignation() {
        val tokens = listOf(
            Token(DataType.DECLARATION_VARIABLE, "", Pair(0,0), Pair(3,0)),
            Token(DataType.VARIABLE_NAME, "x", Pair(4,0), Pair(5,0)),
            Token(DataType.DOUBLE_DOTS, "", Pair(6,0), Pair(7,0)),
            Token(DataType.STRING_TYPE, "", Pair(8,0), Pair(13,0)),
            Token(DataType.ASSIGNATION, "", Pair(14,0), Pair(15,0)),
            Token(DataType.STRING_VALUE, "5", Pair(16,0), Pair(17,0))
        )
        val declarationAssignedBuilder = DeclarationAssignedBuilder()
        val ast = declarationAssignedBuilder.build(tokens)

        assertTrue(ast is VarDeclarationAssignation)

        ast as VarDeclarationAssignation

        assertEquals(DataType.STRING_TYPE, ast.varDeclaration.type.getType())
        assertEquals("", ast.varDeclaration.type.getValue())
    }

}