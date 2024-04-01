package rules

import astn.AST
import astn.Method
import enforcers.Enforcer
import enforcers.LineJumpOnMethodEnforcer

/**
 * This class represents the rule for methods in the PrintScript application.
 * It enforces the rule by using the LineJumpOnMethodEnforcer.
 *
 * @property ammountOfJumpLine The name of the property for the amount of jump line.
 * @property enforcer A list of enforcers that enforce the rule.
 * @property OperationRule An instance of the OperationRule class which is used to enforce the operation rule.
 */
class MethodRule(
    private val ammountOfJumpLine: String,
    override val enforcer: List<Enforcer> = listOf(),
    private val OperationRule: OperationRule = OperationRule(),
) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        val enfocers: MutableList<Enforcer> = enforcer.toMutableList()
        if (property.containsKey(ammountOfJumpLine)) {
            enfocers.add(LineJumpOnMethodEnforcer(property[ammountOfJumpLine] as Int))
        }
        return MethodRule(ammountOfJumpLine, enfocers)
    }

    override fun enforceRule(code: String): String {
        var line = code
        for (enforcer in enforcer) {
            line = enforcer.enforceRule(line)
        }
        return line
    }

    override fun genericLine(ast: AST): String {
        when (ast) {
            is Method -> {
                val newLine = StringBuilder()
                newLine.append(ast.methodName.getValue())
                newLine.append("(")
                // Missing OPTree Rule
                val parameters = OperationRule.genericLine(ast.value)
                newLine.append(OperationRule.enforceRule(parameters))
                newLine.append(")")
                return newLine.toString()
            }
            else -> {
                return ""
            }
        }
    }
}
