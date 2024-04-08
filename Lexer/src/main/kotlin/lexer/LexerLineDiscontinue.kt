package lexer

import org.example.lexer.LexerInterface
import token.Token

class LexerLineDiscontinue() : LexerInterface {
    override fun lex(
        line: String,
        numberLine: Int,
    ): List<Token> {
        TODO("Not yet implemented")
    }

    override fun isLineFinished(): Boolean {
        TODO("Not yet implemented")
    }
}
