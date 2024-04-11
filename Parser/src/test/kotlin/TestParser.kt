import astn.EmptyAST
import astn.VarDeclarationAssignation
import exceptions.SyntacticError
import impl.ParserImpl
import lexer.LexerImpl
import lexer.TokenRegexRule
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestParser {
    val tokenRulesMap: Map<String, TokenRegexRule> =
        mapOf(
            "STRING_VALUE" to TokenRegexRule("\"(?:\\\\.|[^\"])*\"", DataType.STRING_VALUE),
            "DECLARATION_VARIABLE" to TokenRegexRule("\\blet\\b", DataType.DECLARATION_VARIABLE),
            "OPERATOR_PLUS" to TokenRegexRule("\\+", DataType.OPERATOR_PLUS),
            "OPERATOR_MINUS" to TokenRegexRule("-", DataType.OPERATOR_MINUS),
            "OPERATOR_MULTIPLY" to TokenRegexRule("\\*", DataType.OPERATOR_MULTIPLY),
            "OPERATOR_DIVIDE" to TokenRegexRule("/", DataType.OPERATOR_DIVIDE),
            "DOUBLE_DOTS" to TokenRegexRule(":", DataType.DOUBLE_DOTS),
            "SEMICOLON" to TokenRegexRule(";", DataType.SEPARATOR),
            "ASSIGNATION" to TokenRegexRule("=", DataType.ASSIGNATION),
            "LEFT_PARENTHESIS" to TokenRegexRule("\\(", DataType.LEFT_PARENTHESIS),
            "RIGHT_PARENTHESIS" to TokenRegexRule("\\)", DataType.RIGHT_PARENTHESIS),
            "METHOD_CALL" to TokenRegexRule("\\b\\w+\\s*\\((?:[^()]*|\\([^()]*\\))*\\)", DataType.METHOD_CALL),
            "COMA" to TokenRegexRule(",", DataType.COMA),
            "NUMBER_TYPE" to TokenRegexRule("\\bnumber\\b", DataType.NUMBER_TYPE),
            "STRING_TYPE" to TokenRegexRule("\\bstring\\b", DataType.STRING_TYPE),
            "NUMBER_VALUE" to TokenRegexRule("\\b\\d+\\.?\\d*\\b", DataType.NUMBER_VALUE),
            "VARIABLE_NAME" to TokenRegexRule("(?<!\")\\b[a-zA-Z_][a-zA-Z0-9_]*\\b(?!\")", DataType.VARIABLE_NAME),
        )

    @Test
    fun testVariableDeclaration() {
        val parser = ParserImpl()
        val lexerImpl = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let x: number = 5;", 1)
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
