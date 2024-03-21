package Parser.ASTBuilders

import ASTN.*
import Parser.exceptions.UnexpectedTokenException
import Token.DataType
import Token.Token
import Parser.ASTBuilders.AstBuilder.Companion.takeCommentsAndSemiColon

class OperationBuilder : AstBuilder {

    override fun isValid(tokens: List<Token>): Boolean {
        TODO("Not yet implemented")
    }

    override fun build(tokens: List<Token>): AST {
        return Operation(buildOperation(tokens))
    }

    fun buildOperation(tokens: List<Token>): OpTree {
        val postfix = infixToPostfix(takeCommentsAndSemiColon(tokens))
        val nodes = mutableListOf<OpTree>()

        for (token in postfix) {
            when (token.type) {
                !in listOf(
                    DataType.OPERATOR_PLUS,
                    DataType.OPERATOR_MINUS,
                    DataType.OPERATOR_MULTiPLY,
                    DataType.OPERATOR_DIVIDE
                ) -> {
                    when (token.type) {
                        DataType.NUMBER_VALUE -> nodes.add(OperationNumber(token))
                        DataType.STRING_VALUE -> nodes.add(OperationString(token))
                        DataType.VARIABLE_NAME -> nodes.add(OperationVariable(token))
                        else -> throw UnexpectedTokenException("Unexpected token at: ${token.location.first}, ${token.location.second}")
                    }
                }

                else -> {
                    val right = nodes.removeLast()
                    val left = nodes.removeLast()
                    nodes.add(OperationHead(token, left, right))
                }
            }
        }
        return nodes.removeLast()
    }

    private fun precedence(op: Token): Int {
        return when (op.type) {
            DataType.OPERATOR_PLUS, DataType.OPERATOR_MINUS -> 1
            DataType.OPERATOR_MULTiPLY, DataType.OPERATOR_DIVIDE -> 2
            else -> 0
        }
    }

    // Shunting yard algorithm
    private fun infixToPostfix(tokens: List<Token>): List<Token> {
        val stack = mutableListOf<Token>()
        val postfix = mutableListOf<Token>()

        for (token in tokens) {
            when (token.type) {
                DataType.NUMBER_VALUE, DataType.STRING_VALUE, DataType.VARIABLE_NAME -> {
                    postfix.add(token)
                }

                DataType.OPERATOR_PLUS, DataType.OPERATOR_MINUS, DataType.OPERATOR_MULTiPLY, DataType.OPERATOR_DIVIDE -> {
                    while (stack.isNotEmpty() && precedence(stack.last()) >= precedence(token)) {
                        postfix.add(stack.removeLast())
                    }
                    stack.add(token)
                }

                DataType.LEFT_PARENTHESIS -> {
                    stack.add(token)
                }

                DataType.RIGHT_PARENTHESIS -> {
                    while (stack.isNotEmpty() && stack.last().type != DataType.LEFT_PARENTHESIS) {
                        postfix.add(stack.removeLast())
                    }
                    stack.removeLast()
                }

                else -> throw UnexpectedTokenException("Unexpected token at: ${token.location.first}, ${token.location.second}")
            }
        }
        while (stack.isNotEmpty()) {
            postfix.add(stack.removeLast())
        }
        return postfix
    }

}