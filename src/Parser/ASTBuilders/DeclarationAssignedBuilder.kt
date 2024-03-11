package Parser.ASTBuilders

import ASTN.AST
import ASTN.VarDeclarationAssignation
import Token.Token
import Parser.ASTBuilders.AstBuilder.Companion.takeCommentsAndSemiColon
import Token.DataType


class DeclarationAssignedBuilder : AstBuilder {
    val declaratorBuilder = DeclaratorBuilder()
    val operationBuilder = OperationBuilder()
    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
         return declaratorBuilder.isValid(parsedTokens.subList(0,4)) && parsedTokens[5].type == DataType.ASIGNATION_EQUALS
    }

    override fun build(tokens: List<Token>): AST {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        return VarDeclarationAssignation(declaratorBuilder.build(parsedTokens.subList(0,4)), operationBuilder.buildOperation(parsedTokens.subList(6, parsedTokens.size)))

    }
}