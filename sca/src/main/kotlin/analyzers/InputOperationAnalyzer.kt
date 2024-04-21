package analyzers.analyzers

import analyzers.Analyzer
import astn.AST
import astn.Assignation
import astn.IfStatement
import astn.Method
import astn.OpTree
import astn.OperationHead
import astn.OperationMethod
import astn.VarDeclarationAssignation

class InputOperationAnalyzer : Analyzer {
    override fun analyze(ast: AST): String {
        return when (ast) {
            is VarDeclarationAssignation -> checkInputOperation(ast.value)
            is Assignation -> checkInputOperation(ast.value)
            is Method -> checkInputOperation(ast.value)
            is IfStatement -> checkInputOperation(ast.condition)
            else -> ""
        }
    }

    private fun checkInputOperation(opTree: OpTree): String {
        return if (opTree is OperationMethod) {
            if (opTree.value is OperationHead) {
                return "Invalid operation in line ${(opTree.value as OperationHead).operator.getInitialPosition().first}," +
                    " row ${(opTree.value as OperationHead).operator.getInitialPosition().second}"
            } else {
                return ""
            }
        } else {
            ""
        }
    }
}
