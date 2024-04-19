package analyzers

import analyzers.analyzers.InputOperationAnalyzer
import astn.AST

class SCAImpl(options: Map<String, Boolean>) : SCA {
    private val analyzerList = mutableListOf<Analyzer>()

    init {
        buildSCA(options)
    }

    override fun buildSCA(objectBoolMap: Map<String, Boolean>) {
        if (objectBoolMap.isEmpty()) {
            analyzerList.add(CamelCaseAnalyzer())
            return
        }
        objectBoolMap.forEach { (name, value) ->
            addAnalyzer(name, value)
        }
    }

    private fun addAnalyzer(
        name: String,
        value: Boolean,
    ) {
        if (value) {
            when (name) {
                "CamelCaseFormat" -> analyzerList.add(CamelCaseAnalyzer())
                "SnakeCaseFormat" -> analyzerList.add(SnakeCaseAnalyzer())
                "MethodNoExpression" -> analyzerList.add(MethodOperationAnalyzer())
                "InputNoExpression" -> analyzerList.add(InputOperationAnalyzer())
                else -> {}
            }
        }
    }

    override fun readAst(ast: AST): String {
        for (analyzer in analyzerList) {
            val response = analyzer.analyze(ast)
            if (response != "") {
                return response
            }
        }
        return ""
    }
}
