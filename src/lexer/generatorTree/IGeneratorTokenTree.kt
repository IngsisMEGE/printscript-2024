package lexer.generatorTree

import Token.Token
import lexer.Rule

interface IGeneratorTokenTree {
    val rules: List<Rule>
    val child: List<IGeneratorTokenTree>?

    fun generateToken(): List<Token>?
}