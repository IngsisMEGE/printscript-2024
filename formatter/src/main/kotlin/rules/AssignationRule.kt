package rules

import astn.AST
import astn.Assignation
import enforcers.AddSeparatorAtTheEndEnforcer
import enforcers.Enforcer
import enforcers.SpaceForCharacterEnforcer

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
    override fun generateEnforcers(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = enforcer

        enforcers =
            enforcers.plus(
                SpaceForCharacterEnforcer.create(
                    "=".first(),
                    assignationSpaceInFrontName,
                    assignationSpaceInBackName,
                    property,
                ),
            )

        enforcers = enforcers.plus(AddSeparatorAtTheEndEnforcer())

        return AssignationRule(assignationSpaceInFrontName, assignationSpaceInBackName, enforcers, operationRule.isTheRuleIncluded())
    }

    override fun enforceRule(code: String): String {
        var line = code
        for (enforcer in enforcer) {
            line = enforcer.enforceRule(line)
        }
        return line
    }

    override fun <T : AST> genericLine(ast: T): String {
        if (ast is Assignation) {
            val newLine = StringBuilder()
            newLine.append(ast.varName.getValue())
            newLine.append("=")
            val value = operationRule.genericLine(ast.value)
            newLine.append(operationRule.enforceRule(value))
            return newLine.toString()
        }
        return ""
    }

    override fun canCreateGenericLine(ast: AST): Boolean {
        return ast is Assignation
    }
}
