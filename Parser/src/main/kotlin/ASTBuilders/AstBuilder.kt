package Parser.ASTBuilders

import ASTN.AST
import Parser.exceptions.SyntacticError
import Parser.exceptions.UnexpectedTokenException
import token.DataType
import token.Token


interface AstBuilder {
    fun isValid(tokens: List<Token>): Boolean
    fun build(tokens: List<Token>): AST

    companion object {

        fun takeCommentsAndSemiColon(tokens: List<Token>): List<Token> {
            return tokens.filter { token ->
                token.getType() != DataType.SEMICOLON
            }
        }

        fun checkMinLength(tokens: List<Token>, minSize: Int, type: String) {
            if (tokens.size < minSize) throw SyntacticError("Malformed $type at line ${tokens[0].getInitialPosition().second} ")
        }

        fun checkMaxLength(tokens: List<Token>, maxSize: Int, type: String) {
            if (tokens.size > maxSize) throw SyntacticError("Malformed $type at line ${tokens[0].getInitialPosition().second} ")
        }

        fun checkTokenType(token: Token, s: String, types: List<DataType>) {
            if (!types.contains(token.getType())) {
                throw UnexpectedTokenException("$s expected at: ${token.getInitialPosition().first}, ${token.getInitialPosition().second}")
            }
        }
    }
}