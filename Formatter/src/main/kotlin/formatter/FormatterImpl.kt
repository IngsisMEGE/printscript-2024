package formatter

import astn.AST
import rules.Rules

/**
 * This class represents the implementation of the Formatter interface in the PrintScript application.
 * It formats the Abstract Syntax Tree (AST) according to the rules provided.
 *
 * @property property A map of properties that are used to determine the rules to be included.
 * @property rules A list of rules that are used to format the AST.
 */
class FormatterImpl(override val property: Map<String, Any>, private val rules: List<Rules>) : Formatter {
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
            val newRule = rule.isTheRuleIncluded(property)
            val newLine = newRule.genericLine(ast)
            if (newLine != "") {
                return newRule.enforceRule(newLine) + "\n"
            }
        }
        return ""
    }

    fun getRules(): List<Rules> {
        return rules
    }
}
