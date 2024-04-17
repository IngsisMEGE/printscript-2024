package formatter

import astn.AST
import astn.CloseIfStatement
import astn.IfStatement
import enforcers.IndentedIfElseBlockEnforcer
import rules.Rules
import rules.provider.RuleProvider

/**
 * This class represents the implementation of the Formatter interface in the PrintScript application.
 * It formats the Abstract Syntax Tree (AST) according to the rules provided.
 *
 * @property property A map of properties that are used to determine the rules to be included.
 * @property rules A list of rules that are used to format the AST.
 */
class FormatterImpl(override val property: Map<String, Any>) : Formatter {
    private var ifElseBlockEnforcer: IndentedIfElseBlockEnforcer
    private var rules: List<Rules> = RuleProvider.getRules()

    init {
        val rulesWithEnforcers = rules.map { it.isTheRuleIncluded(property) }

        ifElseBlockEnforcer = IndentedIfElseBlockEnforcer(
            if (property.containsKey("Indentation")) property["Indentation"].toString().toInt() else 4
        )

        rules = rulesWithEnforcers
    }

    /**
     * This function formats the given AST according to the rules.
     * It iterates over the rules, includes the rule if it is applicable, generates a line according to the rule, and enforces the rule on the line.
     * If the generated line is not empty, it returns the enforced line with a newline character at the end.
     * If no rule is applicable, it returns an empty string.
     *
     * @param ast The AST to be formatted.
     * @return The formatted string.
     */
    override fun format(ast: AST): String {
        for (rule in rules) {
            if (!rule.canCreateGenericLine(ast)) continue
            ifElseBlockEnforcer.didEnterIf(ast)
            val newLine = rule.genericLine(ast)
            val enforceLine = rule.enforceRule(newLine) + "\n"
            if (ifElseBlockEnforcer.shouldIndent()) {
                val ifIndentEnforce = ifElseBlockEnforcer.enforceRule() + enforceLine
                ifElseBlockEnforcer.didExitIF(ast)
                return ifIndentEnforce
            }
            return enforceLine
        }
        return ""
    }

    override fun changeProperty(property: Map<String, Any>): Formatter {
        return FormatterImpl(property)
    }
}
