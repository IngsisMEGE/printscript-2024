package enforcers

class AddSeparatorAtTheEndEnforcer() : Enforcer {
    private val separatorSymbol: String = ";"

    override fun enforceRule(code: String): String {
        return code + separatorSymbol
    }
}
