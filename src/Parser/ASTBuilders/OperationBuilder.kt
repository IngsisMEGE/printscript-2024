package Parser.ASTBuilders

import ASTN.*
import Parser.exceptions.UnexpectedTokenException
import Token.DataType
import Token.Token

class OperationBuilder : AstBuilder {
    fun buildOperation(tokens: List<Token>): OpTree {
        if (tokens[0].type == DataType.NUMBER_VALUE) return OperationNumber(tokens[0])
        if (tokens[0].type == DataType.STRING_VALUE) return OperationString(tokens[0])
        if (tokens[0].type == DataType.VARIABLE_NAME) return OperationVariable(tokens[0])
        else throw UnexpectedTokenException("Value expected at: ${tokens[0].location.first}, ${tokens[0].location.second}")
    }

    override fun isValid(tokens: List<Token>): Boolean {
        TODO("Not yet implemented")
    }

    override fun build(tokens: List<Token>): AST {
        TODO("Not yet implemented")
    }
}