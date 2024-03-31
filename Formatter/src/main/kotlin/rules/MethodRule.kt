package rules

import astn.AST
import astn.Method
import enforcers.Enforcer
import enforcers.LineJumpOnMethodEnforcer

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
