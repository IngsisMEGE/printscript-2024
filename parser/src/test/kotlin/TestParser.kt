import astn.EmptyAST
import astn.VarDeclaration
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

    @Test
    fun testBooleanAssignation() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(1, 1), Pair(1, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 5), Pair(1, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(1, 6), Pair(1, 6)),
                Token(DataType.BOOLEAN_TYPE, "boolean", Pair(1, 8), Pair(1, 13)),
                Token(DataType.ASSIGNATION, "=", Pair(1, 15), Pair(1, 15)),
                Token(DataType.BOOLEAN_VALUE, "true", Pair(1, 17), Pair(1, 20)),
                Token(DataType.SEPARATOR, ";", Pair(1, 21), Pair(1, 21)),
            )
        val ast = parser.parse(tokens) as VarDeclarationAssignation

        assertNotNull(ast)
        assertEquals("x", ast.varDeclaration.assignation.getValue())
        assertEquals("boolean", ast.varDeclaration.type.getValue())
    }

    @Test
    fun testBooleanDeclaration() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(1, 1), Pair(1, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 5), Pair(1, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(1, 6), Pair(1, 6)),
                Token(DataType.BOOLEAN_TYPE, "boolean", Pair(1, 8), Pair(1, 13)),
                Token(DataType.SEPARATOR, ";", Pair(1, 14), Pair(1, 14)),
            )
        val ast = parser.parse(tokens) as VarDeclaration

        assertNotNull(ast)
        assertEquals("x", ast.assignation.getValue())
        assertEquals("boolean", ast.type.getValue())
    }

    @Test
    fun testBooleanDeclarationAssignation() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(1, 1), Pair(1, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 5), Pair(1, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(1, 6), Pair(1, 6)),
                Token(DataType.BOOLEAN_TYPE, "boolean", Pair(1, 8), Pair(1, 13)),
                Token(DataType.ASSIGNATION, "=", Pair(1, 15), Pair(1, 15)),
                Token(DataType.BOOLEAN_VALUE, "true", Pair(1, 17), Pair(1, 20)),
                Token(DataType.SEPARATOR, ";", Pair(1, 21), Pair(1, 21)),
            )
        val ast = parser.parse(tokens) as VarDeclarationAssignation

        assertNotNull(ast)
        assertEquals("x", ast.varDeclaration.assignation.getValue())
        assertEquals("boolean", ast.varDeclaration.type.getValue())
    }

    @Test
    fun testBooleanMultiplyNumberShouldError() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(1, 1), Pair(1, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 5), Pair(1, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(1, 6), Pair(1, 6)),
                Token(DataType.BOOLEAN_TYPE, "boolean", Pair(1, 8), Pair(1, 13)),
                Token(DataType.ASSIGNATION, "=", Pair(1, 15), Pair(1, 15)),
                Token(DataType.BOOLEAN_VALUE, "true", Pair(1, 17), Pair(1, 20)),
                Token(DataType.OPERATOR_MULTIPLY, "*", Pair(1, 22), Pair(1, 22)),
                Token(DataType.NUMBER_VALUE, "5", Pair(1, 24), Pair(1, 24)),
                Token(DataType.SEPARATOR, ";", Pair(1, 25), Pair(1, 25)),
            )
        assertThrows<SyntacticError> {
            parser.parse(tokens)
        }
    }

    @Test
    fun testBooleanPlusNumberShouldNotError() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(1, 1), Pair(1, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 5), Pair(1, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(1, 6), Pair(1, 6)),
                Token(DataType.NUMBER_TYPE, "number", Pair(1, 8), Pair(1, 13)),
                Token(DataType.ASSIGNATION, "=", Pair(1, 15), Pair(1, 15)),
                Token(DataType.BOOLEAN_VALUE, "true", Pair(1, 17), Pair(1, 20)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(1, 22), Pair(1, 22)),
                Token(DataType.NUMBER_VALUE, "5", Pair(1, 24), Pair(1, 24)),
                Token(DataType.SEPARATOR, ";", Pair(1, 25), Pair(1, 25)),
            )
        assertThrows<SyntacticError> {
            parser.parse(tokens)
        }
    }

    @Test
    fun test3minus2plusStringShouldBeCorrect() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(1, 1), Pair(1, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 5), Pair(1, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(1, 6), Pair(1, 6)),
                Token(DataType.STRING_TYPE, "string", Pair(1, 8), Pair(1, 13)),
                Token(DataType.ASSIGNATION, "=", Pair(1, 15), Pair(1, 15)),
                Token(DataType.NUMBER_VALUE, "3", Pair(1, 17), Pair(1, 17)),
                Token(DataType.OPERATOR_MINUS, "-", Pair(1, 19), Pair(1, 19)),
                Token(DataType.NUMBER_VALUE, "2", Pair(1, 21), Pair(1, 21)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(1, 23), Pair(1, 23)),
                Token(DataType.STRING_VALUE, "hello", Pair(1, 25), Pair(1, 30)),
                Token(DataType.SEPARATOR, ";", Pair(1, 31), Pair(1, 31)),
            )
        val ast = parser.parse(tokens) as VarDeclarationAssignation

        assertNotNull(ast)
        assertEquals("x", ast.varDeclaration.assignation.getValue())
        assertEquals("string", ast.varDeclaration.type.getValue())
    }

    @Test
    fun test004BooleanPlusStringShouldBeCorrect() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(1, 1), Pair(1, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 5), Pair(1, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(1, 6), Pair(1, 6)),
                Token(DataType.STRING_TYPE, "string", Pair(1, 8), Pair(1, 13)),
                Token(DataType.ASSIGNATION, "=", Pair(1, 15), Pair(1, 15)),
                Token(DataType.BOOLEAN_VALUE, "true", Pair(1, 17), Pair(1, 20)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(1, 22), Pair(1, 22)),
                Token(DataType.STRING_VALUE, "hello", Pair(1, 24), Pair(1, 29)),
                Token(DataType.SEPARATOR, ";", Pair(1, 30), Pair(1, 30)),
            )
        val ast = parser.parse(tokens) as VarDeclarationAssignation

        assertNotNull(ast)
        assertEquals("x", ast.varDeclaration.assignation.getValue())
        assertEquals("string", ast.varDeclaration.type.getValue())
    }
}
