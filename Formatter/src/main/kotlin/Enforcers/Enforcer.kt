package Enforcers

import ASTN.AST

interface Enforcer {
    fun enforceRule(code : String) : String
}