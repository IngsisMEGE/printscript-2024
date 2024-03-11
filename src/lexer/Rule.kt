package lexer

interface Rule {

    fun validate(input: String): Boolean
}