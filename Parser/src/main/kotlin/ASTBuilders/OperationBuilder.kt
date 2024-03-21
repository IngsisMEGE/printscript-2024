package Parser.ASTBuilders

import ASTN.*
import Parser.exceptions.UnexpectedTokenException
import Parser.ASTBuilders.AstBuilder.Companion.takeCommentsAndSemiColon
import Parser.exceptions.SyntacticError
import token.DataType
import token.Token
import java.util.*

class OperationBuilder : AstBuilder {

    private val operators = listOf(
        DataType.OPERATOR_PLUS,
        DataType.OPERATOR_MINUS,
        DataType.OPERATOR_MULTIPLY,
        DataType.OPERATOR_DIVIDE
    )

    private val values = listOf(
        DataType.NUMBER_VALUE,
        DataType.STRING_VALUE,
        DataType.VARIABLE_NAME
    )

    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        return when {
            parsedTokens.isEmpty() -> false
            parsedTokens.size == 1 -> parsedTokens[0].getType() in values
            else -> parsedTokens.size > 2 && parsedTokens.any { it.getType() in operators } && parsedTokens.any { it.getType() in values }
        }
    }

    override fun build(tokens: List<Token>): AST {
        return Operation(buildOperation(tokens))
    }

    fun buildOperation(tokens: List<Token>): OpTree {
        val postfix = infixToPostfix(takeCommentsAndSemiColon(tokens))
        val nodes = mutableListOf<OpTree>()

        for (token in postfix) {
            when (token.getType()) {
                !in operators -> {
                    when (token.getType()) {
                        DataType.NUMBER_VALUE -> nodes.add(OperationNumber(token))
                        DataType.STRING_VALUE -> nodes.add(OperationString(token))
                        DataType.VARIABLE_NAME -> nodes.add(OperationVariable(token))
                        else -> throw UnexpectedTokenException("Unexpected token at: ${token.getInitialPosition().first}, ${token.getFinalPosition().second}")
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
        return when (op.getType()) {
            DataType.OPERATOR_PLUS, DataType.OPERATOR_MINUS -> 1
            DataType.OPERATOR_MULTIPLY, DataType.OPERATOR_DIVIDE -> 2
            else -> 0
        }
    }

    // Shunting yard algorithm
    private fun infixToPostfix(tokens: List<Token>): List<Token> {
        val stack = Stack<Token>()
        val postfix = mutableListOf<Token>()

        if (tokens.any { it.getType() in (operators - listOf(DataType.OPERATOR_PLUS)) } && tokens.any { it.getType() == DataType.STRING_VALUE }) throw SyntacticError(
            "Invalid string concatenation"
        )

        for (token in tokens) {
            when (token.getType()) {
                DataType.NUMBER_VALUE, DataType.STRING_VALUE, DataType.VARIABLE_NAME -> {
                    postfix.add(token)
                }

                DataType.OPERATOR_PLUS, DataType.OPERATOR_MINUS, DataType.OPERATOR_MULTIPLY, DataType.OPERATOR_DIVIDE -> {
                    while (stack.isNotEmpty() && precedence(stack.peek()) >= precedence(token)) {
                        postfix.add(stack.pop())
                    }
                    stack.push(token)
                }

                DataType.LEFT_PARENTHESIS -> {
                    stack.push(token)
                }

                DataType.RIGHT_PARENTHESIS -> {
                    while (stack.isNotEmpty() && stack.peek().getType() != DataType.LEFT_PARENTHESIS) {
                        postfix.add(stack.pop())
                    }
                    stack.pop()
                }

                else -> throw UnexpectedTokenException("Unexpected token at: ${token.getInitialPosition().first}, ${token.getFinalPosition().second}")
            }
        }
        while (stack.isNotEmpty()) {
            val token = stack.peek()
            if (token.getType() == DataType.LEFT_PARENTHESIS) throw SyntacticError("Invalid ${token.getValue()} at: ${token.getInitialPosition().first}, ${token.getFinalPosition().second}")
            postfix.add(stack.pop())
        }
        return postfix
    }
}
