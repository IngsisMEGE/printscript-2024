package Parser.ASTBuilders

import ASTN.AST
import ASTN.Assignation
import Token.Token
import Parser.ASTBuilders.AstBuilder.Companion.takeCommentsAndSemiColon
import Token.DataType

class AssignationBuilder : AstBuilder {
    val operationBuilder = OperationBuilder()
    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        return parsedTokens[0].type == DataType.VARIABLE_NAME
    }

    override fun build(tokens: List<Token>): AST {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        verifyStructure(parsedTokens)
        return Assignation(parsedTokens[0], operationBuilder.buildOperation(parsedTokens.subList(2, parsedTokens.size)))
    }

    private fun verifyStructure(tokens: List<Token>) {
        AstBuilder.checkMinLength(tokens, 3, "declaration")
        AstBuilder.checkTokenType(tokens[0], "Variable", listOf(DataType.VARIABLE_NAME))
        AstBuilder.checkTokenType(tokens[1], "Equals", listOf(DataType.ASIGNATION_EQUALS))
    }
}