import astn.EmptyAST
import astn.VarDeclarationAssignation
import exceptions.SyntacticError
import impl.ParserImpl
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestParser {
    @Test
    fun testVariableDeclaration() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(1, 1), Pair(1, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 5), Pair(1, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(1, 6), Pair(1, 6)),
                Token(DataType.NUMBER_TYPE, "number", Pair(1, 8), Pair(1, 13)),
                Token(DataType.ASSIGNATION, "=", Pair(1, 15), Pair(1, 15)),
                Token(DataType.NUMBER_VALUE, "5", Pair(1, 17), Pair(1, 17)),
                Token(DataType.SEPARATOR, ";", Pair(1, 18), Pair(1, 18)),
            )

        val ast = parser.parse(tokens) as VarDeclarationAssignation

        assertNotNull(ast)
        assertEquals("x", ast.varDeclaration.assignation.getValue())
        assertEquals("number", ast.varDeclaration.type.getValue())
    }

    @Test
    fun testEmptyTokenListReturnsEmptyAST() {
        val parser = ParserImpl()
        val tokens = emptyList<Token>()

        val ast = parser.parse(tokens)
        assertTrue { ast is EmptyAST }
    }

    @Test
    fun testSyntacticError() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.UNKNOWN, "unknown", Pair(0, 0), Pair(0, 7)),
            )
        assertThrows<SyntacticError> {
            parser.parse(tokens)
        }
    }
}
