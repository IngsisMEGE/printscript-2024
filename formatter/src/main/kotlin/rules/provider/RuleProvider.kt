package rules.provider

import rules.AssignationRule
import rules.ElseRule
import rules.IfRule
import rules.MethodRule
import rules.Rules
import rules.VarDeclarationAssignationRule
import rules.VarDeclarationRule

class RuleProvider() {
    companion object {
        fun getRules(): List<Rules> {
            return listOf(
                VarDeclarationAssignationRule("DotFront", "DotBack", "EqualFront", "EqualBack"),
                MethodRule("ammountOfLines"),
                VarDeclarationRule("SpaceInFront", "SpaceInBack"),
                AssignationRule("EqualFront", "EqualBack"),
                IfRule(),
                ElseRule(),
            )
        }
    }
}
