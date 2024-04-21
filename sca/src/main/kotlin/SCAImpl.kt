package analyzers

import analyzers.analyzers.InputOperationAnalyzer
import astn.AST

class SCAImpl(override val props: Map<String, Boolean>) : SCA {
    private val analyzerList = mutableListOf<Analyzer>()

    init {
        buildSCA(props)
    }

    override fun buildSCA(objectBoolMap: Map<String, Boolean>) {
        if (objectBoolMap.isEmpty()) {
            analyzerList.add(CamelCaseAnalyzer())
            return
        }
        if (!objectBoolMap.keys.containsAll(
                listOf(
                    "CamelCaseFormat",
                    "SnakeCaseFormat",
                    "MethodNoExpression",
                    "InputNoExpression",
                ),
            )
        ) {
            throw IllegalArgumentException(
                "The object must contain the keys CamelCaseFormat, SnakeCaseFormat, MethodNoExpression, and InputNoExpression",
            )
        }
        if (objectBoolMap["CamelCaseFormat"] == true && objectBoolMap["SnakeCaseFormat"] == true) {
            throw IllegalArgumentException(
                "Both CamelCaseFormat and SnakeCaseFormat cannot be true",
            )
        }
        analyzerList.clear()
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
