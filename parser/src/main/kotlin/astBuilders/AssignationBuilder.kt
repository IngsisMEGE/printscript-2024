package astBuilders

import astBuilders.AstBuilder.Companion.mustEndWithSeparator
import astBuilders.AstBuilder.Companion.takeOutSeparator
import astn.AST
import astn.Assignation
import token.DataType
import token.Token

/**
 * This class is responsible for building assignation ASTs in the PrintScript application.
 * It implements the AstBuilder interface and overrides the isValid and build functions.
 *
 * The isValid function takes a list of tokens as input and checks if they represent a valid assignation. It removes comments and semicolons from the list, and then checks if the first token is a VARIABLE_NAME token. It returns false if the list contains less than three tokens.
 *
 * The build function takes a list of tokens as input and builds an Assignation AST from them. It first removes comments and semicolons from the list, then verifies the structure of the tokens, and finally creates an Assignation object with the first token and the result of the buildOperation function of an OperationBuilder object.
 *
 * The verifyStructure function checks the structure of a list of tokens. It checks if the list contains at least three tokens, if the first token is a VARIABLE_NAME token, and if the second token is an ASSIGNATION token.
 *
 * @throws UnexpectedTokenException If the list of tokens does not represent a valid assignation, if an unexpected token is encountered, or if a token is encountered at an unexpected position.
 */
class AssignationBuilder : AstBuilder {
    private val operationBuilder = OperationBuilder()

    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeOutSeparator(tokens)
        if (parsedTokens.size < 3) return false
        return parsedTokens[0].getType() == DataType.VARIABLE_NAME
    }

    override fun build(tokens: List<Token>): AST {
        verifyStructure(tokens)
        val parsedTokens = takeOutSeparator(tokens)
        return Assignation(parsedTokens[0], operationBuilder.buildOperation(parsedTokens.subList(2, parsedTokens.size)))
    }

    private fun verifyStructure(tokens: List<Token>) {
        mustEndWithSeparator(tokens.last())
        AstBuilder.checkMinLength(tokens, 4, "declaration")
        AstBuilder.checkTokenType(tokens[0], "Variable", listOf(DataType.VARIABLE_NAME))
        AstBuilder.checkTokenType(tokens[1], "Equals", listOf(DataType.ASSIGNATION))
    }
}
