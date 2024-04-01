package rules

import astn.AST
import astn.VarDeclaration
import enforcers.DoubleDotDeclarationEnforcer
import enforcers.Enforcer

class VarDeclarationRule(
    private val DoubleDotSpaceInFront: String,
    private val DoubleDotSpaceInBack: String,
    override val enforcer: List<Enforcer> = listOf(),
) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = listOf()
        if (property.containsKey(DoubleDotSpaceInFront) && property.containsKey(DoubleDotSpaceInBack)) {
            enforcers =
                enforcer.plus(
                    DoubleDotDeclarationEnforcer(
                        property[DoubleDotSpaceInFront] as Int,
                        property[DoubleDotSpaceInBack] as Int,
                    ),
                )
        }
        return VarDeclarationRule(DoubleDotSpaceInFront, DoubleDotSpaceInBack, enforcers)
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
            is VarDeclaration -> {
                val newLine = StringBuilder()
                newLine.append("let ")
                newLine.append(ast.assignation.getValue())
                newLine.append(":")
                newLine.append(ast.type.getValue())
                newLine.append(";")

                return newLine.toString()
            }

            else -> {
                return ""
            }
        }
    }
}
