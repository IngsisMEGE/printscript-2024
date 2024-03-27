package Parser.ASTBuilders

import ASTN.AST
import ASTN.Assignation
import Parser.ASTBuilders.AstBuilder.Companion.takeCommentsAndSemiColon
import token.DataType
import token.Token

class AssignationBuilder : AstBuilder {
    private val operationBuilder = OperationBuilder()
    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        if (parsedTokens.size < 3) return false
        return parsedTokens[0].getType() == DataType.VARIABLE_NAME
    }

    override fun build(tokens: List<Token>): AST {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        verifyStructure(parsedTokens)
        return Assignation(parsedTokens[0], operationBuilder.buildOperation(parsedTokens.subList(2, parsedTokens.size)))
    }

    private fun verifyStructure(tokens: List<Token>) {
        AstBuilder.checkMinLength(tokens, 3, "declaration")
        AstBuilder.checkTokenType(tokens[0], "Variable", listOf(DataType.VARIABLE_NAME))
        AstBuilder.checkTokenType(tokens[1], "Equals", listOf(DataType.ASSIGNATION))
    }
}