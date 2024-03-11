package lexer.generatorTree

import Token.Token
import lexer.Rule

interface GeneratorTokenTree {
    val rules: List<Rule>
    val child: List<GeneratorTokenTree>?

    fun generateToken(): List<Token>?
}