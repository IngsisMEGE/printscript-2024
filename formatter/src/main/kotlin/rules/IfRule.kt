package rules

import astn.AST
import astn.IfStatement
import enforcers.Enforcer
import enforcers.SpaceForCharacterEnforcer

class IfRule(override val enforcer: List<Enforcer> = listOf(), private val operationRule: OperationRule = OperationRule()) :
    Rules {
    override fun generateEnforcers(property: Map<String, Any>): Rules {
        var enforcers: List<Enforcer> = enforcer
        enforcers =
            enforcers.plus(
                SpaceForCharacterEnforcer('{', 1, 0),
            )

        return IfRule(enforcers, operationRule)
    }

    override fun enforceRule(code: String): String {
        var line = code
        for (enforcer in enforcer) {
            line = enforcer.enforceRule(line)
        }
        return line
    }

    override fun <T : AST> genericLine(ast: T): String {
        if (ast is IfStatement) {
            val newLine = StringBuilder()
            newLine.append("if")
            newLine.append("(")
            val parameters = operationRule.genericLine(ast.condition)
            newLine.append(operationRule.enforceRule(parameters))
            newLine.append(")")
            newLine.append("{")
            return newLine.toString()
        }
        return ""
    }

    override fun canCreateGenericLine(ast: AST): Boolean {
        return ast is IfStatement
    }
}
