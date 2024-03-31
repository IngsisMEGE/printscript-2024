package formatter

import astn.AST
import rules.Rules

class FormatterImpl(override val property: Map<String, Any>, private val rules: List<Rules>) : Formatter {
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
}
