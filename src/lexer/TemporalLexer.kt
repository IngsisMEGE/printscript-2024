package lexer

import token.DataType
import token.Token

class TemporalLexer {
    val lexer : Lexer = Lexer(listOf(
        RegexTokenGenerator("\\blet\\b", DataType.LET_KEYWORD, true),
        RegexTokenGenerator("(?<=\\d)\\+|\\+(?=\\d)", DataType.OPERATOR_PLUS, true),
        RegexTokenGenerator("(?<=\\d)-|-(?=\\d)", DataType.OPERATOR_MINUS, true),
        RegexTokenGenerator("(?<=\\d)\\*|\\*(?=\\d)", DataType.OPERATOR_MULTIPLY, true),
        RegexTokenGenerator("(?<=\\d)/|\\/(?=\\d)", DataType.OPERATOR_DIVIDE, true),
        RegexTokenGenerator("=", DataType.ASIGNATION_EQUALS, true),
        RegexTokenGenerator(":", DataType.DOUBLE_DOTS, true),
        RegexTokenGenerator("\\b\\w+\\s*\\([^()]*\\)", DataType.METHOD_CALL, false),
        RegexTokenGenerator("\\(", DataType.LEFT_PARENTHESIS, true),
        RegexTokenGenerator("\\)", DataType.RIGHT_PARENTHESIS, true),
        RegexTokenGenerator("\\b\\d+\\.?\\d*\\b", DataType.NUMBER_VALUE, false),
        RegexTokenGenerator("\\w+", DataType.VARIABLE_NAME, false),
    )
    )

    fun lex(line: String, numberLine : Int):  List<Token> {
        return ListTokenManager.orderTokens(lexer.lex(line, numberLine))
    }
}