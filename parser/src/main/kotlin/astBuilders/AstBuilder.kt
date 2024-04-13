package astBuilders

import astn.AST
import exceptions.SyntacticError
import exceptions.UnexpectedTokenException
import token.DataType
import token.Token

/**
 * This interface defines the contract for building ASTs in the PrintScript application.
 * It provides two main functions: isValid and build.
 *
 * The isValid function takes a list of tokens as input and checks if they represent a valid AST. The specific validation rules depend on the implementation.
 *
 * The build function takes a list of tokens as input and builds an AST from them. The specific building process depends on the implementation.
 *
 * The companion object provides utility functions for common operations in the building process, such as removing comments and semicolons from a list of tokens, checking the minimum and maximum length of a list of tokens, and checking the type of a token.
 *
 * @throws SyntacticError If the list of tokens does not represent a valid AST, if an unexpected token is encountered, or if a token is encountered at an unexpected position.
 * @throws UnexpectedTokenException If the list of tokens does not represent a valid AST, if an unexpected token is encountered, or if a token is encountered at an unexpected position.
 */
interface AstBuilder {
    fun isValid(tokens: List<Token>): Boolean

    fun build(tokens: List<Token>): AST

    companion object {
        fun takeCommentsAndSemiColon(tokens: List<Token>): List<Token> {
            return tokens.filter { token ->
                token.getType() != DataType.SEPARATOR
            }
        }

        fun checkMinLength(
            tokens: List<Token>,
            minSize: Int,
            type: String,
        ) {
            if (tokens.size < minSize) throw SyntacticError("Malformed $type at line ${tokens[0].getInitialPosition().second} ")
        }

        fun checkMaxLength(
            tokens: List<Token>,
            maxSize: Int,
            type: String,
        ) {
            if (tokens.size > maxSize) throw SyntacticError("Malformed $type at line ${tokens[0].getInitialPosition().second} ")
        }

        fun checkTokenType(
            token: Token,
            s: String,
            types: List<DataType>,
        ) {
            if (!types.contains(token.getType())) {
                throw UnexpectedTokenException("$s expected at: ${token.getInitialPosition().first}, ${token.getInitialPosition().second}")
            }
        }
    }
}
