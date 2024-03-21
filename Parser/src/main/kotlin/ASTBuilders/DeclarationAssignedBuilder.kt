package Parser.ASTBuilders

import ASTN.AST
import ASTN.VarDeclarationAssignation
import Parser.ASTBuilders.AstBuilder.Companion.takeCommentsAndSemiColon
import token.DataType
import token.Token


class DeclarationAssignedBuilder : AstBuilder {
    private val declaratorBuilder = DeclaratorBuilder()
    private val operationBuilder = OperationBuilder()
    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        if (parsedTokens.size < 5) return false
        return declaratorBuilder.isValid(
            parsedTokens.subList(
                0,
                4
            )
        ) && parsedTokens[4].getType() == DataType.ASIGNATION_EQUALS
    }

    override fun build(tokens: List<Token>): AST {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        return VarDeclarationAssignation(
            declaratorBuilder.build(parsedTokens.subList(0, 4)),
            operationBuilder.buildOperation(parsedTokens.subList(5, parsedTokens.size))
        )

    }
}