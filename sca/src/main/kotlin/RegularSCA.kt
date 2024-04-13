package analyzers

import astn.AST

class RegularSCA(options: Map<String, Boolean>) {
    val analyzerList = mutableListOf<Analyzer>()

    init {
        buildSCA(options)
    }

    private fun buildSCA(objectBoolMap: Map<String, Boolean>) {
        if (objectBoolMap.isEmpty()) {
            analyzerList.add(CamelCaseAnalyzer())
            return
        }
        objectBoolMap.forEach { (name, value) ->
            addAnalyzer(name, value)
        }
    }

    private fun addAnalyzer(
        nombre: String,
        valor: Boolean,
    ) {
        if (valor) {
            when (nombre) {
                "CamelCaseFormat" -> analyzerList.add(CamelCaseAnalyzer())
                "SnakeCaseFormat" -> analyzerList.add(SnakeCaseAnalyzer())
                "MethodNoExpresion" -> analyzerList.add(MethodOperationAnalyzer())
                else -> {}
            }
        }
    }

    fun readAst(ast: AST): String {
        for (analyzer in analyzerList) {
            val response = analyzer.analyze(ast)
            if (response != "") {
                return response
            }
        }
        return ""
    }
}
