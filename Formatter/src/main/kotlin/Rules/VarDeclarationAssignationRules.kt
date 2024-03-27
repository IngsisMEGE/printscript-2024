package Rules

import ASTN.AST
import Enforcers.AssignationSpaceEnforcer
import Enforcers.DoubleDotDeclarationEnforcer
import Enforcers.Enforcer

class VarDeclarationAssignationRules(
    override val enforcer: List<Enforcer> = listOf(),
    private val DoubleDotSpaceInFrontName: String,
    private val DoubleDotSpaceInBackName: String,
    private val AssignationSpaceInFrontName: String,
    private val AssignationSpaceInBackName: String
) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = listOf()
        if (property[DoubleDotSpaceInFrontName] == true) {
            if (property[DoubleDotSpaceInBackName] == true) {
                enforcers = enforcer.plus(
                    DoubleDotDeclarationEnforcer(
                        property[DoubleDotSpaceInFrontName] as Int,
                        property[DoubleDotSpaceInBackName] as Int
                    )
                )
            }
        }
        if (property[AssignationSpaceInFrontName] == true) {
            if (property[AssignationSpaceInBackName] == true) {
                enforcers = enforcer.plus(
                    AssignationSpaceEnforcer(
                        property[AssignationSpaceInFrontName] as Int,
                        property[AssignationSpaceInBackName] as Int
                    )
                )
            }
        }
        return VarDeclarationAssignationRules(
            enforcers,
            DoubleDotSpaceInFrontName,
            DoubleDotSpaceInBackName,
            AssignationSpaceInFrontName,
            AssignationSpaceInBackName
        )
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
            is ASTN.VarDeclarationAssignation -> {
                val newLine = StringBuilder()
                newLine.append("let ")
                newLine.append(ast.varDeclaration.assignation.getValue())
                newLine.append(" :")
                newLine.append(ast.varDeclaration.type.getValue())
                newLine.append(" =")
                newLine.append(";")

                var line = newLine.toString()

                //Hacer el var Declaration Rule del Operation. Y despues meterle la regla aca.
                return line
            }

            else -> {
                return ""
            }
        }
    }
}