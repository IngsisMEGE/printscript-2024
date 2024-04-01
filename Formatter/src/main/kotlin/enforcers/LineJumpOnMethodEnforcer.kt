package enforcers

class LineJumpOnMethodEnforcer(private val ammountOfSpacers: Int) : Enforcer {
    init {
        if (ammountOfSpacers < 0) {
            throw IllegalArgumentException("The ammount of space must be greater than 0 : Line Jump On Method")
        }
    }

    override fun enforceRule(code: String): String {
        val modifiedCode = StringBuilder()
        modifiedCode.append("\n".repeat(ammountOfSpacers))
        modifiedCode.append(code)
        return modifiedCode.toString()
    }
}
