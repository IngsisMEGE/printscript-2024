package rules

import astn.AST
import astn.VarDeclarationAssignation
import enforcers.AddSeparatorAtTheEndEnforcer
import enforcers.Enforcer
import enforcers.SpaceForCharacterEnforcer

/**
 * This class represents the rule for variable declaration with assignation in the PrintScript application.
 * It enforces the rule by using the DoubleDotDeclarationEnforcer and AssignationSpaceEnforcer.
 *
 * @property doubleDotSpaceInFrontName The name of the property for the space in front of the double dot.
 * @property doubleDotSpaceInBackName The name of the property for the space in back of the double dot.
 * @property assignationSpaceInFrontName The name of the property for the space in front of the assignation.
 * @property assignationSpaceInBackName The name of the property for the space in back of the assignation.
 * @property enforcer A list of enforcers that enforce the rule.
 * @property operationRule An instance of the OperationRule class which is used to enforce the operation rule.
 */
class VarDeclarationAssignationRule(
    private val doubleDotSpaceInFrontName: String,
    private val doubleDotSpaceInBackName: String,
    private val assignationSpaceInFrontName: String,
    private val assignationSpaceInBackName: String,
    override val enforcer: List<Enforcer> = listOf(),
    private val operationRule: OperationRule = OperationRule(),
) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = enforcer
        operationRule.isTheRuleIncluded()

        enforcers =
            enforcers.plus(
                SpaceForCharacterEnforcer.create(
                    ":".first(),
                    doubleDotSpaceInFrontName,
                    doubleDotSpaceInBackName,
                    property,
                ),
            )

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

        return VarDeclarationAssignationRule(
            doubleDotSpaceInFrontName,
            doubleDotSpaceInBackName,
            assignationSpaceInFrontName,
            assignationSpaceInBackName,
            enforcers,
            operationRule.isTheRuleIncluded(),
        )
    }

    override fun enforceRule(code: String): String {
        var line = code
        for (enforcer in enforcer) {
            line = enforcer.enforceRule(line)
        }
        return line
    }

    override fun <T : AST> genericLine(ast: T): String {
        if (ast is VarDeclarationAssignation) {
            val newLine = StringBuilder()
            newLine.append(if (ast.varDeclaration.isMutable) "let " else "const ")
            newLine.append(ast.varDeclaration.assignation.getValue())
            newLine.append(":")
            newLine.append(ast.varDeclaration.type.getValue())
            newLine.append("=")
            val operation = operationRule.genericLine(ast.value)
            newLine.append(operationRule.enforceRule(operation))

            return newLine.toString()
        }
        return ""
    }

    override fun canCreateGenericLine(ast: AST): Boolean {
        return ast is VarDeclarationAssignation
    }
}
