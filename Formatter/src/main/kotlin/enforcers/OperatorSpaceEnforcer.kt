package enforcers

/**
 * This class is responsible for enforcing the rule of having spaces around operators in the PrintScript application.
 * It implements the Enforcer interface and overrides the enforceRule function.
 *
 * The enforceRule function iterates over each character in the provided code string. If the character is an operator and not inside a string,
 * it appends a space before and after the operator to the modified code string. All other characters are appended as they are.
 *
 * Note: This class considers the following characters as operators: '+', '-', '*', '/', '%'.
 */
class OperatorSpaceEnforcer() : Enforcer {
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

            if (!insideString) {
                if (char == '+' || char == '-' || char == '*' || char == '/' || char == '%') {
                    modifiedCode.append(" ")
                    modifiedCode.append(char)
                    modifiedCode.append(" ")
                } else {
                    modifiedCode.append(char)
                }
            } else {
                modifiedCode.append(char)
            }
        }

        return modifiedCode.toString()
    }
}
