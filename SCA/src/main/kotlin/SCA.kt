package analyzers

import astn.AST

interface SCA {
    fun buildSCA(objectBoolMap: Map<String, Boolean>)

    fun readAst(ast: AST): String
}
