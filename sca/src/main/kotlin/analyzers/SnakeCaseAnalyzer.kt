package analyzers

import astn.AST
import astn.VarDeclaration
import astn.VarDeclarationAssignation

class SnakeCaseAnalyzer : Analyzer {
    override fun analyze(ast: AST): String {
        return when (ast) {
            is VarDeclaration -> checkSnakeCase(ast.varName.getValue(), ast.varName.getInitialPosition())
            is VarDeclarationAssignation ->
                checkSnakeCase(
                    ast.varDeclaration.varName.getValue(),
                    ast.varDeclaration.varName.getInitialPosition(),
                )
            else -> ""
        }
    }

    private fun checkSnakeCase(
        str: String,
        location: Pair<Int, Int>,
    ): String {
        return if (!Regex("[a-z]+(?:_[a-z]+)*").matches(str)) {
            "Invalid typing format in line ${location.first} row ${location.second}"
        } else {
            ""
        }
    }
}
