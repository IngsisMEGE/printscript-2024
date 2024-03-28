package Enforcers

class AssignationSpaceEnforcer(private val ammountOfSpaceInFront: Int, private val ammountOfSpaceInBack: Int) :
    Enforcer {

    init {
        if (ammountOfSpaceInFront < 0 || ammountOfSpaceInBack < 0) {
            throw IllegalArgumentException("The ammount of space in front and back must be greater than 0 : Assignation")
        }
    }

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
                modifiedCode.append(" ".repeat(ammountOfSpaceInBack))
                modifiedCode.append(char)
                modifiedCode.append(" ".repeat(ammountOfSpaceInFront))
            } else {
                modifiedCode.append(char)
            }
        }

        return modifiedCode.toString()
    }
}