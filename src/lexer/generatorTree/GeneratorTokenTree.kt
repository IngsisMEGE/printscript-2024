package lexer.generatorTree

import Token.Token
import lexer.Rule

class GeneratorTokenTree(override val rules: List<Rule>, override val child: List<IGeneratorTokenTree>?) : IGeneratorTokenTree {
    override fun generateToken(): List<Token>? {
        TODO("Not yet implemented")
    }


}