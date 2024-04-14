package astBuilders

import astBuilders.AstBuilder.Companion.takeOutSeparator
import astn.AST
import astn.Method
import token.DataType
import token.Token

/**
 * This class is responsible for building method calls in the PrintScript application.
 * It implements the AstBuilder interface and overrides the isValid and build functions.
 *
 * The isValid function takes a list of tokens as input and checks if they represent a valid method call. It removes comments and semicolons from the list, and then checks if the first token is a METHOD_CALL token. It returns false if the list contains less than four tokens.
 *
 * The build function takes a list of tokens as input and builds a Method AST from them. It first removes comments and semicolons from the list, then verifies the structure of the tokens, and finally creates a Method object with the first token and the result of the buildOperation function of an OperationBuilder object.
 *
 * The verifyStructure function checks the structure of a list of tokens. It checks if the list contains at least four tokens, if the first token is a METHOD_CALL token, if the second token is a LEFT_PARENTHESIS token, and if the last token is a RIGHT_PARENTHESIS token.
 *
 * @throws UnexpectedTokenException If the list of tokens does not represent a valid method call, if an unexpected token is encountered, or if a token is encountered at an unexpected position.
 */

class MethodBuilder(private val isCompleteLine : Boolean) : AstBuilder {
    private val operatorBuilder = OperationBuilder()

    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeOutSeparator(tokens)
        if (parsedTokens.size < 4) return false
        return parsedTokens[0].getType() == DataType.METHOD_CALL
    }

    override fun build(tokens: List<Token>): AST {
        verifyStructure(tokens)
        val parsedTokens = takeOutSeparator(tokens)
        return Method(parsedTokens[0], operatorBuilder.buildOperation(parsedTokens.subList(2, parsedTokens.size - 1)))
    }

    private fun verifyStructure(tokens: List<Token>) {
        if (isCompleteLine) {
            AstBuilder.mustEndWithSeparator(tokens.last())
        }
        AstBuilder.checkMinLength(tokens, 4, "declaration")
        AstBuilder.checkTokenType(tokens[0], "Method", listOf(DataType.METHOD_CALL))
        AstBuilder.checkTokenType(tokens[1], "Parenthesis", listOf(DataType.LEFT_PARENTHESIS))
        AstBuilder.checkTokenType(tokens[tokens.size - 2], "Parenthesis", listOf(DataType.RIGHT_PARENTHESIS))
    }
}
