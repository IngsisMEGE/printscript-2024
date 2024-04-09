package analyzers

import astn.AST
import astn.VarDeclaration
import astn.VarDeclarationAssignation

class CamelCaseAnalyzer : Analyzer {
    override fun analyze(ast: AST): String {
        return when (ast) {
            is VarDeclaration -> checkSnakeCase(ast.assignation.getValue(), ast.assignation.getInitialPosition())
            is VarDeclarationAssignation ->
                checkSnakeCase(
                    ast.varDeclaration.assignation.getValue(),
                    ast.varDeclaration.assignation.getInitialPosition(),
                )
            else -> ""
        }
    }

    private fun checkSnakeCase(
        str: String,
        location: Pair<Int, Int>,
    ): String {
        return if (!Regex("[a-z]+(?:[A-Z][a-z]*)*").matches(str)) {
            "Invalid typing format in line ${location.first} row ${location.second}"
        } else {
            ""
        }
    }
}
