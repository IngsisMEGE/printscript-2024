package astBuilders

import astBuilders.AstBuilder.Companion.takeCommentsAndSemiColon
import astn.OpTree
import astn.OperationHead
import astn.OperationNumber
import astn.OperationString
import astn.OperationVariable
import exceptions.SyntacticError
import exceptions.UnexpectedTokenException
import token.DataType
import token.Token
import java.util.Stack

/**
 * This class is responsible for building operations in the PrintScript application.
 * It provides methods to validate and build operations from a list of tokens.
 *
 * The isValid function checks if a list of tokens represents a valid operation. It removes comments and semicolons from the list, and then checks if the list contains any operators and values.
 *
 * The buildOperation function builds an OpTree from a list of tokens. It first checks if the list of tokens represents a valid operation. If it does, it converts the list of tokens to postfix notation and then builds the OpTree.
 *
 * The infixToPostfix function converts a list of tokens from infix notation to postfix notation using the shunting yard algorithm. It throws a SyntacticError if the list of tokens contains an invalid string concatenation or an unmatched left parenthesis.
 *
 * The isValidPostfix function checks if a list of tokens in postfix notation represents a valid operation. It returns a pair of a Boolean and a Token. The Boolean is true if the list of tokens represents a valid operation, and the Token is the invalid token if the list of tokens does not represent a valid operation.
 *
 * @throws UnexpectedTokenException If the list of tokens does not represent a valid operation, if an unexpected token is encountered, or if a token is encountered at an unexpected position.
 * @throws SyntacticError If the list of tokens contains an invalid string concatenation, an unmatched left parenthesis, or an invalid token.
 */
class OperationBuilder {
    private val operators =
        listOf(
            DataType.OPERATOR_PLUS,
            DataType.OPERATOR_MINUS,
            DataType.OPERATOR_MULTIPLY,
            DataType.OPERATOR_DIVIDE,
        )

    private val values =
        listOf(
            DataType.NUMBER_VALUE,
            DataType.STRING_VALUE,
            DataType.VARIABLE_NAME,
        )

    fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        return when {
            parsedTokens.isEmpty() -> false
            parsedTokens.size == 1 -> parsedTokens[0].getType() in values
            else -> parsedTokens.size > 2 && parsedTokens.any { it.getType() in operators } && parsedTokens.any { it.getType() in values }
        }
    }

    fun buildOperation(tokens: List<Token>): OpTree {
        if (!isValid(tokens)) throw UnexpectedTokenException("Invalid operation at Line ${tokens[0].getInitialPosition().first}")

        val postfix = infixToPostfix(takeCommentsAndSemiColon(tokens))
        if (!isValidPostfix(postfix).first) {
            throw SyntacticError(
                "Invalid token ${isValidPostfix(postfix).second?.getValue()} at: ${
                    isValidPostfix(
                        postfix,
                    ).second?.getInitialPosition()?.first
                }, ${isValidPostfix(postfix).second?.getFinalPosition()?.second}",
            )
        }
        val nodes = mutableListOf<OpTree>()

        for (token in postfix) {
            when (token.getType()) {
                !in operators -> {
                    when (token.getType()) {
                        DataType.NUMBER_VALUE -> nodes.add(OperationNumber(token))
                        DataType.STRING_VALUE -> nodes.add(OperationString(token))
                        DataType.VARIABLE_NAME -> nodes.add(OperationVariable(token))
                        else -> throw UnexpectedTokenException(
                            "Unexpected token at: ${token.getInitialPosition().first}, ${token.getFinalPosition().second}",
                        )
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

        for (token in tokens) {
            when (token.getType()) {
                in values -> {
                    postfix.add(token)
                }

                in operators -> {
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

                else -> throw UnexpectedTokenException(
                    "Unexpected token at: ${token.getInitialPosition().first}, ${token.getFinalPosition().second}",
                )
            }
        }
        while (stack.isNotEmpty()) {
            val token = stack.peek()
            if (token.getType() == DataType.LEFT_PARENTHESIS) {
                throw SyntacticError(
                    "Invalid ${token.getValue()} at: ${token.getInitialPosition().first}, ${token.getFinalPosition().second}",
                )
            }
            postfix.add(stack.pop())
        }
        return postfix
    }

    private fun isValidPostfix(tokens: List<Token>): Pair<Boolean, Token?> {
        val stack = Stack<Token>()

        for (token in tokens) {
            when (token.getType()) {
                in values -> {
                    stack.push(token)
                }

                in operators -> {
                    if (stack.size < 2) {
                        return Pair(false, token)
                    } else {
                        val right = stack.pop()
                        val left = stack.pop()
                        if (!checkTypeCompatibility(left, right, token)) {
                            throw SyntacticError(
                                "Invalid operation between ${left.getType()} and ${right.getType()} at:" +
                                    " ${token.getInitialPosition().first}, ${token.getFinalPosition().second}",
                            )
                        }
                        stack.push(token)
                    }
                }

                else -> return Pair(false, token)
            }
        }

        return if (stack.size == 1) {
            Pair(true, null)
        } else {
            Pair(false, stack.peek())
        }
    }

    private fun checkTypeCompatibility(
        left: Token,
        right: Token,
        operator: Token,
    ): Boolean {
        val leftType = left.getType()
        val rightType = right.getType()
        val operatorType = operator.getType()

        if (leftType == DataType.STRING_VALUE || rightType == DataType.STRING_VALUE) {
            return operatorType == DataType.OPERATOR_PLUS
        }

        return true
    }
}
