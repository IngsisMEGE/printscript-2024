import astn.Assignation
import astn.CloseIfStatement
import astn.EmptyAST
import astn.Method
import astn.OperationMethod
import astn.OperationString
import astn.OperationVariable
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import exceptions.SyntacticError
import exceptions.UnexpectedTokenException
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
        assertEquals("x", ast.varDeclaration.varName.getValue())
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
        assertEquals("x", ast.varDeclaration.varName.getValue())
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
        assertEquals("x", ast.varName.getValue())
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
        assertEquals("x", ast.varDeclaration.varName.getValue())
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
        assertThrows<UnexpectedTokenException> {
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
        assertThrows<UnexpectedTokenException> {
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
        assertEquals("x", ast.varDeclaration.varName.getValue())
        assertEquals("string", ast.varDeclaration.type.getValue())
    }

    @Test
    fun test004BooleanPlusStringShouldNotBeCorrect() {
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

        assertThrows<Exception> {
            parser.parse(tokens)
        }
    }

    @Test
    fun test005LeftBracketShouldBeCorrect() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.LEFT_BRACKET, "{", Pair(1, 1), Pair(1, 1)),
            )
        val ast = parser.parse(tokens) as EmptyAST

        assertNotNull(ast)
    }

    @Test
    fun test006RightBracketShouldBeCorrect() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.RIGHT_BRACKET, "}", Pair(1, 1), Pair(1, 1)),
            )
        val ast = parser.parse(tokens) as CloseIfStatement

        assertNotNull(ast)
    }

    @Test
    fun test007ReadInputAssignationTest() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.DECLARATION_VARIABLE, "let", Pair(1, 1), Pair(1, 3)),
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 5), Pair(1, 5)),
                Token(DataType.DOUBLE_DOTS, ":", Pair(1, 6), Pair(1, 6)),
                Token(DataType.STRING_TYPE, "string", Pair(1, 8), Pair(1, 13)),
                Token(DataType.ASSIGNATION, "=", Pair(1, 15), Pair(1, 15)),
                Token(DataType.METHOD_CALL, "readInput", Pair(1, 17), Pair(1, 25)),
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(1, 26), Pair(1, 26)),
                Token(DataType.STRING_VALUE, "Enter your name: ", Pair(1, 27), Pair(1, 45)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(1, 27), Pair(1, 27)),
                Token(DataType.SEPARATOR, ";", Pair(1, 26), Pair(1, 26)),
            )
        val ast = parser.parse(tokens) as VarDeclarationAssignation

        val opInput = ast.value as OperationMethod

        val input = opInput.value as OperationString

        assertNotNull(ast)
        assertEquals("x", ast.varDeclaration.varName.getValue())
        assertEquals("string", ast.varDeclaration.type.getValue())
        assertEquals("Enter your name: ", input.value.getValue())
    }

    @Test
    fun test008ReadInputAssignation() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 1), Pair(1, 1)),
                Token(DataType.ASSIGNATION, "=", Pair(1, 3), Pair(1, 3)),
                Token(DataType.METHOD_CALL, "readInput", Pair(1, 5), Pair(1, 13)),
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(1, 14), Pair(1, 14)),
                Token(DataType.STRING_VALUE, "Enter your name: ", Pair(1, 15), Pair(1, 33)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(1, 34), Pair(1, 34)),
                Token(DataType.SEPARATOR, ";", Pair(1, 35), Pair(1, 35)),
            )

        val ast = parser.parse(tokens) as Assignation

        val opInput = ast.value as OperationMethod

        val input = opInput.value as OperationString

        assertNotNull(ast)
        assertEquals("x", ast.varName.getValue())
        assertEquals("Enter your name: ", input.value.getValue())
    }

    @Test
    fun test009CannotMakeOperationWithReadInput() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 1), Pair(1, 1)),
                Token(DataType.ASSIGNATION, "=", Pair(1, 3), Pair(1, 3)),
                Token(DataType.METHOD_CALL, "readInput", Pair(1, 5), Pair(1, 13)),
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(1, 14), Pair(1, 14)),
                Token(DataType.STRING_VALUE, "Enter your name: ", Pair(1, 15), Pair(1, 33)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(1, 34), Pair(1, 34)),
                Token(DataType.OPERATOR_PLUS, "+", Pair(1, 35), Pair(1, 35)),
                Token(DataType.NUMBER_VALUE, "5", Pair(1, 37), Pair(1, 37)),
                Token(DataType.SEPARATOR, ";", Pair(1, 38), Pair(1, 38)),
            )

        assertThrows<UnexpectedTokenException> {
            parser.parse(tokens)
        }
    }

    @Test
    fun test010MethodCallWithVariable() {
        val parser = ParserImpl()
        val tokens =
            listOf(
                Token(DataType.METHOD_CALL, "print", Pair(1, 1), Pair(1, 5)),
                Token(DataType.LEFT_PARENTHESIS, "(", Pair(1, 6), Pair(1, 6)),
                Token(DataType.VARIABLE_NAME, "x", Pair(1, 7), Pair(1, 7)),
                Token(DataType.RIGHT_PARENTHESIS, ")", Pair(1, 8), Pair(1, 8)),
                Token(DataType.SEPARATOR, ";", Pair(1, 9), Pair(1, 9)),
            )

        val ast = parser.parse(tokens) as Method

        val opVar = ast.value as OperationVariable

        assertNotNull(ast)
        assertEquals("print", ast.methodName.getValue())
        assertEquals("x", opVar.varName.getValue())
    }
}
