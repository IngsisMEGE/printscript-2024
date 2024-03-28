
package Rules

import ASTN.AST
import Enforcers.DoubleDotDeclarationEnforcer
import Enforcers.Enforcer

class VarDeclarationRule(override val enforcer: List<Enforcer> = listOf()) : Rules {
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
        return VarDeclarationRule(enforcers)
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
            is ASTN.VarDeclaration -> {
                val newLine = StringBuilder()
                newLine.append("let ")
                newLine.append(ast.assignation.getValue())
                newLine.append(":")
                newLine.append(ast.type.getValue())
                newLine.append(";")

                var line = newLine.toString()
                for (enforcer in enforcer) {
                    line = enforcer.enforceRule(line)
                }
                return line
            }

            else -> {
                return ""
            }
        }
    }
}
