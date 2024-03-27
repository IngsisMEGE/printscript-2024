package Rules

import ASTN.AST
import Enforcers.AssignationSpaceEnforcer
import Enforcers.DoubleDotDeclarationEnforcer
import Enforcers.Enforcer

class VarDeclarationAssignationRules(override val enforcer: List<Enforcer> = listOf()) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = listOf()
        if (property["DoubleDotSpaceInFront"] == true) {
            if (property["DoubleDotSpaceInBack"] == true) {
                enforcers = enforcer.plus(
                    DoubleDotDeclarationEnforcer(
                        property["DoubleDotSpaceInFront"] as Int,
                        property["DoubleDotSpaceInBack"] as Int
                    )
                )
            }
        }
        if (property["AssignationSpaceInFront"] == true) {
            if (property["AssignationSpaceInBack"] == true) {
                enforcers = enforcer.plus(
                    AssignationSpaceEnforcer(
                        property["AssignationSpaceInFront"] as Int,
                        property["AssignationSpaceInBack"] as Int
                    )
                )
            }
        }
        return VarDeclarationAssignationRules(enforcers)
    }

    override fun enforceRule(ast: AST, code: String): String {
        when (ast) {
            is ASTN.VarDeclarationAssignation -> {
                val newLine = StringBuilder()
                newLine.append("let ")
                newLine.append(ast.varDeclaration.assignation.getValue())
                newLine.append(" :")
                newLine.append(ast.varDeclaration.type.getValue())
                newLine.append(" =")
                newLine.append(";")

                var line = newLine.toString()
                for (enforcer in enforcer) {
                    line = enforcer.enforceRule(ast, line)
                }

                //Hacer el var Declaration Rule del Operation. Y despues meterle la regla aca.
                return line
            }
            else -> {
                return code
            }
        }
    }
}