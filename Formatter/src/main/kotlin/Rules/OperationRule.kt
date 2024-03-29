package Rules

import ASTN.*
import Enforcers.Enforcer
import Enforcers.OperatorSpaceEnforcer
import token.DataType
import token.Token

class OperationRule(override val enforcer: List<Enforcer> = listOf()) : Rules{
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        return OperationRule(listOf(OperatorSpaceEnforcer()))
    }

    override fun enforceRule(code: String): String {
        var line = code
        for (enforcer in enforcer) {
            line = enforcer.enforceRule(line)
        }
        return line
    }

    override fun genericLine(ast: AST): String {
        return when (ast) {
            is Operation -> postfixToInfix(ast.value) //deberia ser un OpTree
            else -> ""
        }
    }

    private fun postfixToInfix(postfixAST: OpTree): String {
        return when (postfixAST) {
            is OperationHead -> {
                val right = postfixToInfix(postfixAST.right)
                val left = postfixToInfix(postfixAST.left)
                val operator = postfixAST.operator.getValue()

                val leftExpression = if (postfixAST.left is OperationHead && precedence((postfixAST.left as OperationHead).operator) < precedence(postfixAST.operator)) "($left)" else left
                val rightExpression = if (postfixAST.right is OperationHead && precedence((postfixAST.right as OperationHead).operator) <= precedence(postfixAST.operator)) "($right)" else right

                "$leftExpression$operator$rightExpression"
            }
            is OperationNumber-> postfixAST.value.getValue()
            is OperationString -> "\""+ postfixAST.value.getValue() + "\""
            is OperationVariable -> postfixAST.value.getValue()
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