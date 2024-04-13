package enforcers

class SpaceForCharacterEnforcer(
    private val char: Char,
    private val amountOfSpaceInFront: Int,
    private val amountOfSpaceInBack: Int,
) : Enforcer {
    companion object {
        private fun createWithInFront(
            char: Char,
            amountOfSpaceInFront: Int,
        ): SpaceForCharacterEnforcer {
            return SpaceForCharacterEnforcer(char, amountOfSpaceInFront, 0)
        }

        private fun createWithInBack(
            char: Char,
            amountOfSpaceInBack: Int,
        ): SpaceForCharacterEnforcer {
            return SpaceForCharacterEnforcer(char, 0, amountOfSpaceInBack)
        }

        private fun createBoth(
            char: Char,
            amountOfSpaceInFront: Int,
            amountOfSpaceInBack: Int,
        ): SpaceForCharacterEnforcer {
            return SpaceForCharacterEnforcer(char, amountOfSpaceInFront, amountOfSpaceInBack)
        }

        fun create(
            char: Char,
            propertyNameSpaceInFront: String,
            propertyNameSpaceInBack: String,
            property: Map<String, Any>,
        ): SpaceForCharacterEnforcer {
            return if (property.containsKey(propertyNameSpaceInFront) && property.containsKey(propertyNameSpaceInBack)) {
                createBoth(
                    char,
                    property[propertyNameSpaceInFront].toString().toInt(),
                    property[propertyNameSpaceInBack].toString().toInt(),
                )
            } else if (property.containsKey(propertyNameSpaceInFront)) {
                createWithInFront(char, property[propertyNameSpaceInFront].toString().toInt())
            } else if (property.containsKey(propertyNameSpaceInBack)) {
                createWithInBack(char, property[propertyNameSpaceInBack].toString().toInt())
            } else {
                SpaceForCharacterEnforcer(char, 0, 0)
            }
        }
    }

    init {
        if (amountOfSpaceInFront < 0) {
            throw IllegalArgumentException(
                "The amount of space in front must be greater than or equal to 0 for \"$char\" amount = $amountOfSpaceInFront",
            )
        } else if (amountOfSpaceInBack < 0) {
            throw IllegalArgumentException(
                "The amount of space in back must be greater than or equal to 0 for \"$char\" amount = $amountOfSpaceInBack",
            )
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

            if (char == this.char && !insideString) {
                modifiedCode.append(" ".repeat(amountOfSpaceInFront))
                modifiedCode.append(char)
                modifiedCode.append(" ".repeat(amountOfSpaceInBack))
            } else {
                modifiedCode.append(char)
            }
        }

        return modifiedCode.toString()
    }
}
