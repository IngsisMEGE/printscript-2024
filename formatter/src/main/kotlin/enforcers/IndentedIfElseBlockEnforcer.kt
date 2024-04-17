package enforcers

import astn.AST
import astn.CloseIfStatement
import astn.IfStatement

class IndentedIfElseBlockEnforcer(private val amountOfIndentation: Int) {
    private var ifCounter : Int = 0;

    init {
        if (amountOfIndentation < 0) {
            throw IllegalArgumentException("The amount of indentation must be greater than or equal to 0")
        }
    }

    fun enforceRule(): String {
        if (amountOfIndentation == 0 || ifCounter == 0) return ""
        val modifiedCode = StringBuilder()
        modifiedCode.append(" ".repeat(amountOfIndentation * ifCounter))
        return modifiedCode.toString()
    }

    fun didEnterIf(ast : AST){
        if (ast is IfStatement ) ifCounter++
        if (ast is CloseIfStatement && ast.isElse) ifCounter++
    }

    fun didExitIF(ast : AST){
        if (ast is CloseIfStatement && ifCounter > 0) {
            ifCounter--
        }
    }

    fun shouldIndent() : Boolean {
        return ifCounter > 0
    }

}
