package Rules

import ASTN.AST
import Enforcers.Enforcer

interface Rules {
    val enforcer : Enforcer
    fun isTheRuleIncluded(ast : AST, property : String) : Boolean
    fun enforceRule(code : String) : String
}