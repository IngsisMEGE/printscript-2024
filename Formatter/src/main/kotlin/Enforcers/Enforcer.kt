package Enforcers

import ASTN.AST

interface Enforcer {
    fun enforceRule(ast : AST, code : String) : String
}