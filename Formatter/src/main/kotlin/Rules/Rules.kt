package Rules

import ASTN.AST
import Enforcers.Enforcer

interface Rules {
    val enforcer : Enforcer
    fun isTheRuleIncluded( property : String) : Boolean
    fun enforceRule(ast : AST , code : String) : String
}