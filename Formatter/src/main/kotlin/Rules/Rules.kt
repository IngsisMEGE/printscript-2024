package Rules

import ASTN.AST
import Enforcers.Enforcer

interface Rules {
    val enforcer : List<Enforcer>
    fun isTheRuleIncluded( property : Map<String , Any>) : Rules
    fun enforceRule(ast : AST , code : String) : String
}