package rules

import astn.AST
import astn.VarDeclaration
import enforcers.AddSeparatorAtTheEndEnforcer
import enforcers.Enforcer
import enforcers.SpaceForCharacterEnforcer

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
    override fun generateEnforcers(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = listOf()

        enforcers =
            enforcers.plus(
                SpaceForCharacterEnforcer.create(
                    ":".first(),
                    doubleDotSpaceInFront,
                    doubleDotSpaceInBack,
                    property,
                ),
            )

        enforcers = enforcers.plus(AddSeparatorAtTheEndEnforcer())

        return VarDeclarationRule(doubleDotSpaceInFront, doubleDotSpaceInBack, enforcers)
    }

    override fun enforceRule(code: String): String {
        var line = code
        for (enforcer in enforcer) {
            line = enforcer.enforceRule(line)
        }
        return line
    }

    override fun <T : AST> genericLine(ast: T): String {
        if (ast is VarDeclaration) {
            val newLine = StringBuilder()
            newLine.append("let ")
            newLine.append(ast.varName.getValue())
            newLine.append(":")
            newLine.append(ast.type.getValue())
            return newLine.toString()
        }
        return ""
    }

    override fun canCreateGenericLine(ast: AST): Boolean {
        return ast is VarDeclaration
    }
}
