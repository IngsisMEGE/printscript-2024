package enforcers

/**
 * This class enforces the rule of having a specific amount of spaces around the double dot (:) in the PrintScript application.
 * It implements the Enforcer interface and overrides the enforceRule function.
 *
 * The enforceRule function iterates over each character in the provided code string. If the character is a double dot and not inside a string,
 * it appends a specified number of spaces before and after the double dot to the modified code string. All other characters are appended as they are.
 *
 * @property ammountOfSpaceInFront The number of spaces to append in front of the double dot.
 * @property ammountOfSpaceInBack The number of spaces to append after the double dot.
 * @throws IllegalArgumentException If the number of spaces is less than 0.
 */
class DoubleDotDeclarationEnforcer(private var ammountOfSpaceInFront: Int, private var ammountOfSpaceInBack: Int) : Enforcer {
    init {
        if (ammountOfSpaceInFront < 0 || ammountOfSpaceInBack < 0) {
            throw IllegalArgumentException("The ammount of space in front and back must be greater than 0 : Double Dot")
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

            if (char == ':' && !insideString) {
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
