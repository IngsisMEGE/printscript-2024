package rules

import astn.AST
import astn.VarDeclaration
import enforcers.DoubleDotDeclarationEnforcer
import enforcers.Enforcer

/**
 * This class represents the rule for variable declaration in the PrintScript application.
 * It enforces the rule by using the DoubleDotDeclarationEnforcer.
 *
 * @property doubleDotSpaceInFront The name of the property for the space in front of the double dot.
 * @property doubleDotSpaceInBack The name of the property for the space in back of the double dot.
 * @property enforcer A list of enforcers that enforce the rule.
 */
class VarDeclarationRule(
    private val doubleDotSpaceInFront: String,
    private val doubleDotSpaceInBack: String,
    override val enforcer: List<Enforcer> = listOf(),
) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = listOf()
        if (property.containsKey(doubleDotSpaceInFront) && property.containsKey(doubleDotSpaceInBack)) {
            enforcers =
                enforcer.plus(
                    DoubleDotDeclarationEnforcer(
                        property[doubleDotSpaceInFront].toString().toInt(),
                        property[doubleDotSpaceInBack].toString().toInt(),
                    ),
                )
        }
        return VarDeclarationRule(doubleDotSpaceInFront, doubleDotSpaceInBack, enforcers)
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
