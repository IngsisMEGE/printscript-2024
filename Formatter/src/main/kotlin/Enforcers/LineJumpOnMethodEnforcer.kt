package Enforcers

import ASTN.AST
import ASTN.Method

class LineJumpOnMethodEnforcer(private val ammountOfSpacers : Int) : Enforcer {
    override fun enforceRule(ast: AST, code: String): String {
        val modifiedCode = StringBuilder()
        when (ast) {
            is Method -> {
                modifiedCode.append("\n".repeat(ammountOfSpacers))
                modifiedCode.append(code)
            }
            else -> {
                modifiedCode.append(code)
            }
        }

        return modifiedCode.toString()
    }
}