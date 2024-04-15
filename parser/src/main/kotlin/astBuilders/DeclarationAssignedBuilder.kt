package astBuilders

import astBuilders.AstBuilder.Companion.takeOutSeparator
import astn.AST
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import token.DataType
import token.Token

/**
 * This class is responsible for building variable declarations with assignments in the PrintScript application.
 * It implements the AstBuilder interface and overrides the isValid and build functions.
 *
 * The isValid function takes a list of tokens as input and checks if they represent a valid variable declaration with an assignment. It removes comments and semicolons from the list, and then checks if the first five tokens represent a valid variable declaration and if the fifth token is an ASSIGNATION token.
 *
 * The build function takes a list of tokens as input and builds a VarDeclarationAssignation AST from them. It first removes comments and semicolons from the list, then builds a VarDeclaration object from the first four tokens using a DeclaratorBuilder object, and finally builds an operation from the remaining tokens using an OperationBuilder object.
 *

 */
class DeclarationAssignedBuilder : AstBuilder {
    private val declaratorBuilder = DeclaratorBuilder(false)
    private val operationBuilder = OperationBuilder()

    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeOutSeparator(tokens)
        if (parsedTokens.size <= 5) return false
        return declaratorBuilder.isValid(
            parsedTokens.subList(
                0,
                5,
            ),
        ) && parsedTokens[4].getType() == DataType.ASSIGNATION
    }

    override fun build(tokens: List<Token>): AST {
        return VarDeclarationAssignation(
            declaratorBuilder.build(tokens.subList(0, 4)) as VarDeclaration,
            operationBuilder.buildOperation(tokens.subList(5, tokens.size)),
        )
    }
}
