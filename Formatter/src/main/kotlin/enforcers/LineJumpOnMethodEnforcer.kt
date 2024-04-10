package enforcers

/**
 * This class enforces the rule of adding line jumps before a method in the PrintScript application.
 * It implements the Enforcer interface and overrides the enforceRule function.
 *
 * The enforceRule function prepends a specified number of newline characters to the provided code string.
 *
 * @property amountOfSpacers The number of newline characters to prepend.
 * @throws IllegalArgumentException If the number of newline characters is less than 0.
 */
class LineJumpOnMethodEnforcer(private val amountOfSpacers: Int) : Enforcer {
    companion object {
        private fun createWithSpace(amountOfSpacers: Int): LineJumpOnMethodEnforcer {
            return LineJumpOnMethodEnforcer(amountOfSpacers)
        }

        fun create(
            propertyNameSpace: String,
            property: Map<String, Any>,
        ): LineJumpOnMethodEnforcer {
            return if (property.containsKey(propertyNameSpace)) {
                createWithSpace(property[propertyNameSpace].toString().toInt())
            } else {
                LineJumpOnMethodEnforcer(0)
            }
        }
    }

    init {
        if (amountOfSpacers < 0) {
            throw IllegalArgumentException("The amount of space must be greater than 0 : Line Jump On Method")
        }
    }

    override fun enforceRule(code: String): String {
        val modifiedCode = StringBuilder()
        modifiedCode.append("\n".repeat(amountOfSpacers))
        modifiedCode.append(code)
        return modifiedCode.toString()
    }
}
