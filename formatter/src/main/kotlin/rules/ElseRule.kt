package rules

import astn.AST
import astn.CloseIfStatement
import enforcers.Enforcer
import enforcers.SpaceForCharacterEnforcer

class ElseRule(override val enforcer: List<Enforcer> = listOf()) : Rules {
    override fun isTheRuleIncluded(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = enforcer
        enforcers =
            enforcers.plus(
                SpaceForCharacterEnforcer('{', 1, 0),
            )

        return ElseRule(enforcers)
    }

    override fun enforceRule(code: String): String {
        var line = code
        for (enforcer in enforcer) {
            line = enforcer.enforceRule(line)
        }
        return line
    }

    override fun <T : AST> genericLine(ast: T): String {
        if (ast is CloseIfStatement) {
            val newLine = StringBuilder()
            newLine.append("}")
            if (ast.isElse) {
                newLine.append("else")
                newLine.append("{")
            }
            return newLine.toString()
        }
        return ""
    }

    override fun canCreateGenericLine(ast: AST): Boolean {
        return ast is CloseIfStatement
    }
}
