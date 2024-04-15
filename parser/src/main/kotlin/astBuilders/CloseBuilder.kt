package astBuilders

import astn.AST
import token.DataType
import token.Token

class CloseBuilder() : AstBuilder {
    override fun isValid(tokens: List<Token>): Boolean {
        return tokens.size == 1 && tokens[0].getType() == DataType.RIGHT_BRACKET
    }

    override fun build(tokens: List<Token>): AST {
        verifyStructure(tokens)
        return astn.CloseIfStatement(false)
    }

    private fun verifyStructure(tokens: List<Token>) {
        AstBuilder.checkMinLength(tokens, 1, "closing bracket")
        AstBuilder.checkTokenType(tokens[0], "Closing bracket", listOf(DataType.RIGHT_BRACKET))
    }
}
