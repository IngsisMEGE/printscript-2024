package rules

import astn.OpTree
import astn.OperationBoolean
import astn.OperationHead
import astn.OperationInput
import astn.OperationNumber
import astn.OperationString
import astn.OperationVariable
import enforcers.Enforcer
import enforcers.SpaceForCharacterEnforcer
import token.DataType
import token.Token

/**
 * This class represents the rule for operations in the PrintScript application.
 * It enforces the rule by using the OperatorSpaceEnforcer.
 *
 * @property enforcer A list of enforcers that enforce the rule.
 */
class OperationRule(private val enforcer: List<Enforcer> = listOf()) {
    fun isTheRuleIncluded(): OperationRule {
        return OperationRule(
            listOf(
                SpaceForCharacterEnforcer("+".first(), 1, 1),
                SpaceForCharacterEnforcer("-".first(), 1, 1),
                SpaceForCharacterEnforcer("*".first(), 1, 1),
                SpaceForCharacterEnforcer("/".first(), 1, 1),
                SpaceForCharacterEnforcer("%".first(), 1, 1),
            ),
        )
    }

    fun enforceRule(code: String): String {
        var line = code
        for (enforcer in enforcer) {
            line = enforcer.enforceRule(line)
        }
        return line
    }

    fun genericLine(ast: OpTree): String {
        return postfixToInfix(ast)
    }

    private fun postfixToInfix(postfixAST: OpTree): String {
        return when (postfixAST) {
            is OperationHead -> {
                val right = postfixToInfix(postfixAST.right)
                val left = postfixToInfix(postfixAST.left)
                val operator = postfixAST.operator.getValue()

                val leftExpression =
                    if (postfixAST.left is OperationHead && precedence((postfixAST.left as OperationHead).operator) <
                        precedence(
                            postfixAST.operator,
                        )
                    ) {
                        "($left)"
                    } else {
                        left
                    }
                val rightExpression =
                    if (postfixAST.right is OperationHead && precedence((postfixAST.right as OperationHead).operator) <=
                        precedence(
                            postfixAST.operator,
                        )
                    ) {
                        "($right)"
                    } else {
                        right
                    }

                "$leftExpression$operator$rightExpression"
            }

            is OperationNumber -> postfixAST.value.getValue()
            is OperationString -> "\"" + postfixAST.value.getValue() + "\""
            is OperationVariable -> postfixAST.value.getValue()
            is OperationBoolean -> postfixAST.value.getValue()
            is OperationInput -> "readInput(${postfixToInfix(postfixAST.value)})"
            else -> throw IllegalArgumentException("Invalid OpTree type")
        }
    }

    private fun precedence(op: Token): Int {
        return when (op.getType()) {
            DataType.OPERATOR_PLUS, DataType.OPERATOR_MINUS -> 1
            DataType.OPERATOR_MULTIPLY, DataType.OPERATOR_DIVIDE -> 2
            else -> 0
        }
    }
}
