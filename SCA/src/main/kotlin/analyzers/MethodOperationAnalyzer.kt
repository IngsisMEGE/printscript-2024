package analyzers

import astn.AST
import astn.Method
import astn.OpTree
import astn.OperationHead

class MethodOperationAnalyzer : Analyzer {
    override fun analyze(ast: AST): String {
        return when (ast) {
            is Method -> checkMethodOperation(ast.value)
            else -> ""
        }
    }

    private fun checkMethodOperation(opTree: OpTree): String {
        return if (opTree is OperationHead) {
            return "Invalid operation in line ${opTree.operator.getInitialPosition().first}," +
                " row ${opTree.operator.getInitialPosition().second}"
        } else {
            ""
        }
    }
}
