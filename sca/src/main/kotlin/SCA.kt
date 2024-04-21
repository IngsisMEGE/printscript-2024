package analyzers

import astn.AST

interface SCA {
    val props: Map<String, Boolean>

    fun buildSCA(objectBoolMap: Map<String, Boolean>)

    fun readAst(ast: AST): String
}
