package token

import lexer.LexerImpl
import lexer.TokenRegexRule
import org.example.lexer.Lexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LexerImplTest {
    private val tokenRulesMap: Map<String, TokenRegexRule> =
        mapOf(
            "STRING_VALUE" to TokenRegexRule("\"(?:\\\\.|[^\"])*\"", DataType.STRING_VALUE),
            "DECLARATION_VARIABLE" to TokenRegexRule("\\blet\\b", DataType.DECLARATION_VARIABLE),
            "DECLARATION_CONSTANT" to TokenRegexRule("\\bconst\\b", DataType.DECLARATION_IMMUTABLE),
            "IF_STATEMENT" to TokenRegexRule("\\bif\\b", DataType.IF_STATEMENT),
            "ELSE_STATEMENT" to TokenRegexRule("\\}\\s*else", DataType.ELSE_STATEMENT),
            "OPERATOR_PLUS" to TokenRegexRule("\\+", DataType.OPERATOR_PLUS),
            "OPERATOR_MINUS" to TokenRegexRule("-", DataType.OPERATOR_MINUS),
            "OPERATOR_MULTIPLY" to TokenRegexRule("\\*", DataType.OPERATOR_MULTIPLY),
            "OPERATOR_DIVIDE" to TokenRegexRule("/", DataType.OPERATOR_DIVIDE),
            "DOUBLE_DOTS" to TokenRegexRule(":", DataType.DOUBLE_DOTS),
            "NEWLINE_SEPARATOR" to TokenRegexRule("\n", DataType.SEPARATOR),
            "SEMICOLON" to TokenRegexRule(";", DataType.SEPARATOR),
            "ASSIGNATION" to TokenRegexRule("=", DataType.ASSIGNATION),
            "LEFT_PARENTHESIS" to TokenRegexRule("\\(", DataType.LEFT_PARENTHESIS),
            "RIGHT_PARENTHESIS" to TokenRegexRule("\\)", DataType.RIGHT_PARENTHESIS),
            "LEFT_BRACKET" to TokenRegexRule("\\{", DataType.LEFT_BRACKET),
            "RIGHT_BRACKET" to TokenRegexRule("\\}", DataType.RIGHT_BRACKET),
            "METHOD_CALL" to TokenRegexRule("\\b\\w+\\s*\\((?:[^()]*|\\([^()]*\\))*\\)", DataType.METHOD_CALL),
            "COMA" to TokenRegexRule(",", DataType.COMA),
            "NUMBER_TYPE" to TokenRegexRule("\\bnumber\\b", DataType.NUMBER_TYPE),
            "STRING_TYPE" to TokenRegexRule("\\bstring\\b", DataType.STRING_TYPE),
            "BOOLEAN_TYPE" to TokenRegexRule("\\bboolean\\b", DataType.BOOLEAN_TYPE),
            "BOOLEAN_VALUE" to TokenRegexRule("\\b(?:true|false)\\b", DataType.BOOLEAN_VALUE),
            "NUMBER_VALUE" to TokenRegexRule("\\b\\d+\\.?\\d*\\b", DataType.NUMBER_VALUE),
            "VARIABLE_NAME" to TokenRegexRule("(?<!\")\\b[a-zA-Z_][a-zA-Z0-9_]*\\b(?!\")", DataType.VARIABLE_NAME),
        )

    @Test
    fun lexTest() {
        val line = "let a;"
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val result = lexerImpl.lex(line, 1)
        assertEquals(DataType.DECLARATION_VARIABLE, result[0].getType())
        assertEquals("let", result[0].getValue())
        assertEquals(DataType.VARIABLE_NAME, result[1].getType())
        assertEquals("a", result[1].getValue())
        assertEquals(4, result[1].getInitialPosition().first)
    }

    @Test
    fun lexTest2() {
        val line = "let name = 5;"
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val result = lexerImpl.lex(line, 1)
        assertEquals(DataType.DECLARATION_VARIABLE, result[0].getType())
        assertEquals("let", result[0].getValue())
        assertEquals(DataType.VARIABLE_NAME, result[1].getType())
        assertEquals("name", result[1].getValue())
        assertEquals(DataType.ASSIGNATION, result[2].getType())
        assertEquals("=", result[2].getValue())
        assertEquals(DataType.NUMBER_VALUE, result[3].getType())
        assertEquals("5", result[3].getValue())
        assertEquals(result.size, 5)
    }

    @Test
    fun methodCall() {
        val line = "let letter = sum(5, 5);"
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val result = lexerImpl.lex(line, 1)

        assertEquals(DataType.DECLARATION_VARIABLE, result[0].getType())
        assertEquals("let", result[0].getValue())
        assertEquals(DataType.VARIABLE_NAME, result[1].getType())
        assertEquals("letter", result[1].getValue())
        assertEquals(DataType.ASSIGNATION, result[2].getType())
        assertEquals("=", result[2].getValue())
        assertEquals(DataType.METHOD_CALL, result[3].getType())
        assertEquals("sum", result[3].getValue())
        assertEquals(DataType.LEFT_PARENTHESIS, result[4].getType())
        assertEquals("(", result[4].getValue())
        assertEquals(DataType.NUMBER_VALUE, result[5].getType())
        assertEquals("5", result[5].getValue())
        assertEquals(DataType.COMA, result[6].getType())
        assertEquals(",", result[6].getValue())
        assertEquals(DataType.NUMBER_VALUE, result[7].getType())
        assertEquals("5", result[7].getValue())
        assertEquals(DataType.RIGHT_PARENTHESIS, result[8].getType())
        assertEquals(")", result[8].getValue())
    }

    @Test
    fun testLetKeywordPass() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let \n", 1)
        assertEquals(DataType.DECLARATION_VARIABLE, tokens[0].getType())
    }

    @Test
    fun testLetKeywordTricky() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let letting;", 1)
        assertEquals(DataType.VARIABLE_NAME, tokens[1].getType())
        assertEquals("letting", tokens[1].getValue())
    }

    @Test
    fun testOperatorPlus() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("3 +2;", 1)
        assertEquals(DataType.OPERATOR_PLUS, tokens[1].getType())
        assertEquals("2", tokens[2].getValue())
        assertEquals(3, tokens[2].getInitialPosition().first)
    }

    @Test
    fun testStringLiteral() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("\"This is a string\";", 1)
        assertEquals(DataType.STRING_VALUE, tokens[0].getType())
    }

    @Test
    fun testMethodCall() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("println(\"Hello World\");", 1)
        assertEquals(DataType.METHOD_CALL, tokens[0].getType())
        assertEquals(tokens.size, 5)
    }

    @Test
    fun testNumberValue() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("42;", 1)
        assertEquals(DataType.NUMBER_VALUE, tokens[0].getType())
    }

    @Test
    fun testComplexExpression() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("3 + 4 * (2 - 1);", 1)
        val expectedTypes =
            listOf(
                DataType.NUMBER_VALUE,
                DataType.OPERATOR_PLUS,
                DataType.NUMBER_VALUE,
                DataType.OPERATOR_MULTIPLY,
                DataType.LEFT_PARENTHESIS,
                DataType.NUMBER_VALUE,
                DataType.OPERATOR_MINUS,
                DataType.NUMBER_VALUE,
                DataType.RIGHT_PARENTHESIS,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun testWhitespaceVariation() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let    varName    =    \"value\";", 1)
        assertEquals(DataType.DECLARATION_VARIABLE, tokens[0].getType())
        assertEquals(DataType.VARIABLE_NAME, tokens[1].getType())
        assertEquals("varName", tokens[1].getValue())
        assertEquals(DataType.ASSIGNATION, tokens[2].getType())
        assertEquals(DataType.STRING_VALUE, tokens[3].getType())
        assertEquals("value", tokens[3].getValue())
    }

    @Test
    fun testWithStringValue() { // Variable name se confunde. Tambien hay problema con el "Hello"
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let x: string = \"Hello\";", 1)
        assertEquals(DataType.DECLARATION_VARIABLE, tokens[0].getType())
        assertEquals(DataType.VARIABLE_NAME, tokens[1].getType())
        assertEquals("x", tokens[1].getValue())
        assertEquals(DataType.DOUBLE_DOTS, tokens[2].getType())
        assertEquals(DataType.STRING_TYPE, tokens[3].getType())
        assertEquals(DataType.ASSIGNATION, tokens[4].getType())
        assertEquals(DataType.STRING_VALUE, tokens[5].getType())
        assertEquals("Hello", tokens[5].getValue())
    }

    @Test
    fun testStringWithEscapedCharacters() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("\"Line1\\nLine2\";", 1)
        assertEquals(DataType.STRING_VALUE, tokens[0].getType())
        assertEquals("Line1\\nLine2", tokens[0].getValue())
    }

    @Test
    fun testNestedExpressions() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let result = (3 + (2 * 5));", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.ASSIGNATION,
                DataType.LEFT_PARENTHESIS,
                DataType.NUMBER_VALUE,
                DataType.OPERATOR_PLUS,
                DataType.LEFT_PARENTHESIS,
                DataType.NUMBER_VALUE,
                DataType.OPERATOR_MULTIPLY,
                DataType.NUMBER_VALUE,
                DataType.RIGHT_PARENTHESIS,
                DataType.RIGHT_PARENTHESIS,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun testDeclarationWithTypeNumber() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let a: number= 5;", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.DOUBLE_DOTS,
                DataType.NUMBER_TYPE,
                DataType.ASSIGNATION,
                DataType.NUMBER_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun testDeclarationWithTypeString() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let a: string = 5;", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.DOUBLE_DOTS,
                DataType.STRING_TYPE,
                DataType.ASSIGNATION,
                DataType.NUMBER_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun testMethodCallWithParenthesisInisde() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let a = sum(5, (5 + 5));", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.ASSIGNATION,
                DataType.METHOD_CALL,
                DataType.LEFT_PARENTHESIS,
                DataType.NUMBER_VALUE,
                DataType.COMA,
                DataType.LEFT_PARENTHESIS,
                DataType.NUMBER_VALUE,
                DataType.OPERATOR_PLUS,
                DataType.NUMBER_VALUE,
                DataType.RIGHT_PARENTHESIS,
                DataType.RIGHT_PARENTHESIS,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun test006StringWithSpaceInTheMiddle() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let a = \"Hello World\";", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.ASSIGNATION,
                DataType.STRING_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun test006StringWithSpaceInTheMiddleAndSpecialCharacter() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let a = \"Hello,:+ number World\"; let b : number = 4;", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.ASSIGNATION,
                DataType.STRING_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
        val tokens2 = lexerImpl.lex("let a = \"Hello,:+ number World\"; let b : number = 4;", 1)
        val expectedTypes2 =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.DOUBLE_DOTS,
                DataType.NUMBER_TYPE,
                DataType.ASSIGNATION,
                DataType.NUMBER_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes2, tokens2.map { it.getType() })
    }

    @Test
    fun test007BooleanInDeclaration() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let a: boolean;", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.DOUBLE_DOTS,
                DataType.BOOLEAN_TYPE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun test008BooleanDeclarationAssignation() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let a: boolean = true;", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.DOUBLE_DOTS,
                DataType.BOOLEAN_TYPE,
                DataType.ASSIGNATION,
                DataType.BOOLEAN_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun test009BooleanAssignation() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let a = true;", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.ASSIGNATION,
                DataType.BOOLEAN_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun test010IfStatement() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("if (true) { let a = 5; }", 1)
        val expectedTypes =
            listOf(
                DataType.IF_STATEMENT,
                DataType.LEFT_PARENTHESIS,
                DataType.BOOLEAN_VALUE,
                DataType.RIGHT_PARENTHESIS,
                DataType.LEFT_BRACKET,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })

        val tokens2 = lexerImpl.lex("if (true) { let a = 5; }", 1)
        val expectedTypes2 =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.ASSIGNATION,
                DataType.NUMBER_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes2, tokens2.map { it.getType() })
    }

    @Test
    fun test011IfWithElseStatement() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("if (true) { let a = 5; } else { let b = 6; }", 1)
        val expectedTypes =
            listOf(
                DataType.IF_STATEMENT,
                DataType.LEFT_PARENTHESIS,
                DataType.BOOLEAN_VALUE,
                DataType.RIGHT_PARENTHESIS,
                DataType.LEFT_BRACKET,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })

        val tokens2 = lexerImpl.lex("if (true) { let a = 5; } else { let b = 6; }", 1)
        val expectedTypes2 =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.ASSIGNATION,
                DataType.NUMBER_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes2, tokens2.map { it.getType() })

        val tokens3 = lexerImpl.lex("if (true) { let a = 5; } else { let b = 6; }", 1)
        val expectedTypes3 =
            listOf(
                DataType.ELSE_STATEMENT,
            )

        assertEquals(expectedTypes3, tokens3.map { it.getType() })

        val tokens4 = lexerImpl.lex("if (true) { let a = 5; } else { let b = 6; }", 1)
        val expectedTypes4 =
            listOf(
                DataType.LEFT_BRACKET,
            )

        assertEquals(expectedTypes4, tokens4.map { it.getType() })
    }

    @Test
    fun test012ConstDeclaration() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("const a = 5;", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_IMMUTABLE,
                DataType.VARIABLE_NAME,
                DataType.ASSIGNATION,
                DataType.NUMBER_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun test013ConstDeclarationAssignation() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("const a: number = 5;", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_IMMUTABLE,
                DataType.VARIABLE_NAME,
                DataType.DOUBLE_DOTS,
                DataType.NUMBER_TYPE,
                DataType.ASSIGNATION,
                DataType.NUMBER_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun test014readInput() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let a = readInput();", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.ASSIGNATION,
                DataType.METHOD_CALL,
                DataType.LEFT_PARENTHESIS,
                DataType.RIGHT_PARENTHESIS,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun test015readInputWithParameters() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let a = readInput(\"Enter a number\");", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.ASSIGNATION,
                DataType.METHOD_CALL,
                DataType.LEFT_PARENTHESIS,
                DataType.STRING_VALUE,
                DataType.RIGHT_PARENTHESIS,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }

    @Test
    fun test016DecimalNumberShouldWork() {
        val lexerImpl: Lexer = LexerImpl(tokenRulesMap)
        val tokens = lexerImpl.lex("let a = 5.5;", 1)
        val expectedTypes =
            listOf(
                DataType.DECLARATION_VARIABLE,
                DataType.VARIABLE_NAME,
                DataType.ASSIGNATION,
                DataType.NUMBER_VALUE,
                DataType.SEPARATOR,
            )
        assertEquals(expectedTypes, tokens.map { it.getType() })
    }
}
