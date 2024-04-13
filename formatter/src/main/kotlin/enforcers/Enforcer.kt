package enforcers

interface Enforcer {
    fun enforceRule(code: String): String
}
