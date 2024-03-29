import ASTN.VarDeclarationAssignation
import Parser.exceptions.ParsinException
import Parser.exceptions.SyntacticError
import Parser.impl.ParserImpl
import org.example.lexer.TemporalLexer
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.assertEquals

class TestParser {

    @Test
    fun testParser() {
        assertEquals(2, 1 + 1)
    }
    @Test
    fun testVariableDeclaration() {
        val lexer = TemporalLexer()
        val parser = ParserImpl()

        val tokens = lexer.lex("let x: number = 5;",1)
        val ast = parser.parse(tokens) as VarDeclarationAssignation

        assertNotNull(ast)
        assertEquals("x", ast.varDeclaration.assignation.getValue())
        assertEquals("", ast.varDeclaration.type.getValue())

    }
    @Test
    fun testParsinException() {
        val parser = ParserImpl()
        val tokens = emptyList<Token>()
        assertThrows<ParsinException> {
            parser.parse(tokens)
        }
    }
    @Test
    fun testSyntacticError() {
        val parser = ParserImpl()
        val tokens = listOf(
            Token(DataType.UNKNOWN, "unknown", Pair(0,0), Pair(0,7))
        )
        assertThrows<SyntacticError> {
            parser.parse(tokens)
        }
    }
}