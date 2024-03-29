package Rules

import ASTN.AST
import Enforcers.Enforcer

class AssignationRule(private val AssignationSpaceInFrontName : String, private val AssignationSpaceInBackName : String  , override val enforcer: List<Enforcer> = listOf(), private val OperationRule : OperationRule = OperationRule()) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = enforcer
        if (property.containsKey(AssignationSpaceInFrontName) && property.containsKey(AssignationSpaceInBackName)) {
            enforcers = enforcers.plus(
                Enforcers.AssignationSpaceEnforcer(
                    property[AssignationSpaceInFrontName] as Int,
                    property[AssignationSpaceInBackName] as Int
                )
            )
        }
        return AssignationRule(AssignationSpaceInFrontName, AssignationSpaceInBackName, enforcers)
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
            is ASTN.Assignation -> {
                val newLine = StringBuilder()
                newLine.append(ast.assignation.getValue())
                newLine.append("=")
                val value = OperationRule.genericLine(ast.value)
                newLine.append(OperationRule.enforceRule(value))
                newLine.append(";")
                return newLine.toString()
            }
            else -> {
                return ""
            }
        }
    }
}