package Parser.ASTBuilders

import ASTN.AST
import Parser.Exceptions.SyntacticError
import Parser.Exceptions.UnexpectedTokenException
import Token.DataType
import Token.Token

interface AstBuilder {
    fun isValid(tokens: List<Token>): Boolean
    fun build(tokens: List<Token>): AST
    companion object{

        fun takeCommentsAndSemiColon(tokens: List<Token>): List<Token> {
            return tokens.filter { token ->
                !(
                    token.type == DataType.COMMENT ||
                    token.type == DataType.SEMICOLON
                )
            }
        }
        fun checkMinLength(tokens: List<Token>, minSize: Int, type: String) {
            if (tokens.size < minSize) throw SyntacticError("Malformed ${type} at line ${tokens[0].location.first} ")
        }
        fun checkMaxLength(tokens: List<Token>, maxSize: Int, type: String) {
            if (tokens.size > maxSize) throw SyntacticError("Malformed ${type} at line ${tokens[0].location.first} ")
        }

        fun checkTokenType(token: Token, s: String, types: List<DataType>) {
            if (!types.contains(token.type)) {
                throw UnexpectedTokenException("$s expected at: ${token.location.first}, ${token.location.second}")
            }
        }
    }
}