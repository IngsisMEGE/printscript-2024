package Enforcers

import ASTN.AST

class AssignationSpaceEnforcer(private val ammountOfSpaceInFront: Int, private val ammountOfSpaceInBack: Int) :
    Enforcer {
    override fun enforceRule(code: String): String {
        val modifiedCode = StringBuilder()
        var insideString = false
        var escaped = false

        for (char in code) {
            if (char == '\"' && !escaped) {
                insideString = !insideString
            }
            if (char == '\\') {
                escaped = !escaped
            } else {
                escaped = false
            }

            if (char == '=' && !insideString) {
                modifiedCode.append(" ".repeat(ammountOfSpaceInBack - 1))
                modifiedCode.append(char)
                modifiedCode.append(" ".repeat(ammountOfSpaceInFront))
            } else {
                modifiedCode.append(char)
            }
        }

        return modifiedCode.toString()
    }
}