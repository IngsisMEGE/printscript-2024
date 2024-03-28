package Enforcers

interface Enforcer {
    fun enforceRule(code : String) : String
}