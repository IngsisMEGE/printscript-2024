package rules

import astn.AST
import enforcers.Enforcer

/**
 * This interface represents the rules for the PrintScript application.
 * It is implemented by various classes that represent different types of rules.
 *
 * @property enforcer A list of enforcers that enforce the rule.
 */
interface Rules {
    val enforcer: List<Enforcer>

    fun generateEnforcers(property: Map<String, Any>): Rules

    fun enforceRule(code: String): String

    fun <T : AST> genericLine(ast: T): String

    fun canCreateGenericLine(ast: AST): Boolean
}
