package enforcers

/**
 * This class enforces the rule of having a specific amount of spaces around the assignation operator (=) in the PrintScript application.
 * It implements the Enforcer interface and overrides the enforceRule function.
 *
 * The enforceRule function iterates over each character in the provided code string. If the character is an assignation operator and not inside a string,
 * it appends a specified number of spaces before and after the operator to the modified code string. All other characters are appended as they are.
 *
 * @property amountOfSpaceInFront The number of spaces to append in front of the assignation operator.
 * @property amountOfSpaceInBack The number of spaces to append after the assignation operator.
 * @throws IllegalArgumentException If the number of spaces is less than 0.
 */
class AssignationSpaceEnforcer(private val amountOfSpaceInFront: Int, private val amountOfSpaceInBack: Int) :
    Enforcer {
    init {
        if (amountOfSpaceInFront < 0 || amountOfSpaceInBack < 0) {
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
                modifiedCode.append(" ".repeat(amountOfSpaceInBack))
                modifiedCode.append(char)
                modifiedCode.append(" ".repeat(amountOfSpaceInFront))
            } else {
                modifiedCode.append(char)
            }
        }

        return modifiedCode.toString()
    }
}
