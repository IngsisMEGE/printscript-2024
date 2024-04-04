package rules

import astn.AST
import astn.Assignation
import enforcers.AssignationSpaceEnforcer
import enforcers.Enforcer

/**
 * This class represents the rule for assignation in the PrintScript application.
 * It enforces the rule by using the AssignationSpaceEnforcer.
 *
 * @property assignationSpaceInFrontName The name of the property for the space in front of the assignation.
 * @property assignationSpaceInBackName The name of the property for the space in back of the assignation.
 * @property enforcer A list of enforcers that enforce the rule.
 * @property operationRule An instance of the OperationRule class which is used to enforce the operation rule.
 */
class AssignationRule(
    private val assignationSpaceInFrontName: String,
    private val assignationSpaceInBackName: String,
    override val enforcer: List<Enforcer> = listOf(),
    private val operationRule: OperationRule = OperationRule(),
) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = enforcer
        if (property.containsKey(assignationSpaceInFrontName) && property.containsKey(assignationSpaceInBackName)) {
            enforcers =
                enforcers.plus(
                    AssignationSpaceEnforcer(
                        property[assignationSpaceInFrontName].toString().toInt(),
                        property[assignationSpaceInBackName].toString().toInt(),
                    ),
                )
        }
        return AssignationRule(assignationSpaceInFrontName, assignationSpaceInBackName, enforcers)
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
            is Assignation -> {
                val newLine = StringBuilder()
                newLine.append(ast.assignation.getValue())
                newLine.append("=")
                val value = operationRule.genericLine(ast.value)
                newLine.append(operationRule.enforceRule(value))
                newLine.append(";")
                return newLine.toString()
            }
            else -> {
                return ""
            }
        }
    }
}
