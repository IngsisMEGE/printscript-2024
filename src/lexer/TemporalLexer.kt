package lexer

import token.DataType
import token.Token

class TemporalLexer {
    val lexer : Lexer = Lexer(listOf(
        RegexTokenGenerator("(?:\\b\\w+;\\b|;\\s)", DataType.SEMICOLON, true),
        RegexTokenGenerator("\blet\\b", DataType.LET_KEYWORD, true),
        RegexTokenGenerator("\b(?!(?:keyword1|keyword2|keyword3)\\b)[a-zA-Z][a-zA-Z0-9_]*\\b", DataType.VARIABLE_NAME, true),
    )
    )

    fun lex(line: String, numberLine : Int):  List<Token> {
        return lexer.lex(line, numberLine)
    }
}