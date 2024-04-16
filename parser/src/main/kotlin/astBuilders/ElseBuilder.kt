package astBuilders

import astBuilders.AstBuilder.Companion.takeOutSeparator
import astn.AST
import token.DataType
import token.Token

class ElseBuilder : AstBuilder {
    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeOutSeparator(tokens)
        if (parsedTokens.size != 1) return false
        return parsedTokens[0].getType() == DataType.ELSE_STATEMENT
    }

    override fun build(tokens: List<Token>): AST {
        validateStructure(tokens)
        return astn.CloseIfStatement(true)
    }

    private fun validateStructure(tokens: List<Token>) {
        AstBuilder.checkMinLength(tokens, 1, "else statement")
        AstBuilder.checkTokenType(tokens[0], "Else", listOf(DataType.ELSE_STATEMENT))
    }
}
