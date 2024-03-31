package astBuilders

import astBuilders.AstBuilder.Companion.takeCommentsAndSemiColon
import astn.AST
import astn.Method
import token.DataType
import token.Token

class MethodBuilder : AstBuilder {
    private val operatorBuilder = OperationBuilder()

    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        if (parsedTokens.size < 4) return false
        return parsedTokens[0].getType() == DataType.METHOD_CALL
    }

    override fun build(tokens: List<Token>): AST {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        verifyStructure(parsedTokens)
        return Method(parsedTokens[0], operatorBuilder.buildOperation(parsedTokens.subList(2, parsedTokens.size - 1)))
    }

    private fun verifyStructure(tokens: List<Token>) {
        AstBuilder.checkMinLength(tokens, 4, "declaration")
        AstBuilder.checkTokenType(tokens[0], "Method", listOf(DataType.METHOD_CALL))
        AstBuilder.checkTokenType(tokens[1], "Parenthesis", listOf(DataType.LEFT_PARENTHESIS))
        AstBuilder.checkTokenType(tokens[tokens.size - 1], "Parenthesis", listOf(DataType.RIGHT_PARENTHESIS))
    }
}
