package astBuilders

import astBuilders.AstBuilder.Companion.takeCommentsAndSemiColon
import astn.AST
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import token.DataType
import token.Token

class DeclarationAssignedBuilder : AstBuilder {
    private val declaratorBuilder = DeclaratorBuilder()
    private val operationBuilder = OperationBuilder()

    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        if (parsedTokens.size <= 5) return false
        return declaratorBuilder.isValid(
            parsedTokens.subList(
                0,
                5,
            ),
        ) && parsedTokens[4].getType() == DataType.ASSIGNATION
    }

    override fun build(tokens: List<Token>): AST {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        return VarDeclarationAssignation(
            declaratorBuilder.build(parsedTokens.subList(0, 4)) as VarDeclaration,
            operationBuilder.buildOperation(parsedTokens.subList(5, parsedTokens.size)),
        )
    }
}
