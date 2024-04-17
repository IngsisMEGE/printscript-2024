package enforcers

class IndentedIfElseBlockEnforcer(private val amountOfIndentation: Int, private val ifCounter: Int = 0) : Enforcer {
    companion object {
        private fun createWithIndentation(amountOfIndentation: Int): IndentedIfElseBlockEnforcer {
            return IndentedIfElseBlockEnforcer(amountOfIndentation)
        }

        fun create(
            propertyNameSpace: String,
            property: Map<String, Any>,
        ): IndentedIfElseBlockEnforcer {
            return if (property.containsKey(propertyNameSpace)) {
                createWithIndentation(property[propertyNameSpace].toString().toInt())
            } else {
                IndentedIfElseBlockEnforcer(4)
            }
        }
    }

    init {
        if (amountOfIndentation < 0) {
            throw IllegalArgumentException("The amount of indentation must be greater than or equal to 0")
        }
    }

    override fun enforceRule(code: String): String {
        if (amountOfIndentation == 0 || ifCounter == 0) return code
        val modifiedCode = StringBuilder()
        modifiedCode.append(" ".repeat(amountOfIndentation * ifCounter))
        modifiedCode.append(code)
        return modifiedCode.toString()
    }
}
