package rules

import astn.AST
import astn.Method
import enforcers.AddSeparatorAtTheEndEnforcer
import enforcers.Enforcer
import enforcers.LineJumpOnMethodEnforcer

/**
 * This class represents the rule for methods in the PrintScript application.
 * It enforces the rule by using the LineJumpOnMethodEnforcer.
 *
 * @property amountOfJumpLine The name of the property for the amount of jump line.
 * @property enforcer A list of enforcers that enforce the rule.
 * @property operationRule An instance of the OperationRule class which is used to enforce the operation rule.
 */
class MethodRule(
    private val amountOfJumpLine: String,
    override val enforcer: List<Enforcer> = listOf(),
    private val operationRule: OperationRule = OperationRule(),
) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = enforcer

        enforcers = enforcers.plus(LineJumpOnMethodEnforcer.create(amountOfJumpLine, property))

        enforcers = enforcers.plus(AddSeparatorAtTheEndEnforcer())

        return MethodRule(amountOfJumpLine, enforcers, operationRule.isTheRuleIncluded())
    }

    override fun enforceRule(code: String): String {
        var line = code
        for (enforcer in enforcer) {
            line = enforcer.enforceRule(line)
        }
        return line
    }

    override fun <T : AST> genericLine(ast: T): String {
        if (ast is Method) {
            val newLine = StringBuilder()
            newLine.append(ast.methodName.getValue())
            newLine.append("(")
            val parameters = operationRule.genericLine(ast.value)
            newLine.append(operationRule.enforceRule(parameters))
            newLine.append(")")
            return newLine.toString()
        }
        return ""
    }

    override fun canCreateGenericLine(ast: AST): Boolean {
        return ast is Method
    }
}
