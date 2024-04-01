package enforcers

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
