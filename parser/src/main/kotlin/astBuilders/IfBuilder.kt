package astBuilders

import astBuilders.AstBuilder.Companion.takeOutSeparator
import astn.AST
import token.DataType
import token.Token

class IfBuilder : AstBuilder {
    private val operationBuilder = OperationBuilder()

    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeOutSeparator(tokens)
        if (parsedTokens.size < 3) return false
        return parsedTokens[0].getType() == DataType.IF_STATEMENT
    }

    override fun build(tokens: List<Token>): AST {
        verifyStructure(tokens)
        val parsedTokens = takeOutSeparator(tokens)
        return astn.IfStatement(operationBuilder.buildOperation(parsedTokens.subList(2, parsedTokens.size - 2)))
     }

    private fun verifyStructure(tokens: List<Token>) {
        AstBuilder.checkMinLength(tokens, 5, "if statement")
        AstBuilder.checkMaxLength(tokens, 5, "if statement")
        AstBuilder.checkTokenType(tokens[0], "If", listOf(DataType.IF_STATEMENT))
        AstBuilder.checkTokenType(tokens[1], "Parenthesis", listOf(DataType.LEFT_PARENTHESIS))
        AstBuilder.checkTokenType(tokens[2], "Boolean Value or Variable", listOf(DataType.BOOLEAN_VALUE, DataType.VARIABLE_NAME))
        AstBuilder.checkTokenType(tokens[3], "Parenthesis", listOf(DataType.RIGHT_PARENTHESIS))
        AstBuilder.checkTokenType(tokens[4], "Curly bracket", listOf(DataType.LEFT_BRACKET))
        }
}