package astBuilders

import astn.AST
import token.Token

class EmptyASTBuilder : AstBuilder {
    override fun isValid(tokens: List<Token>): Boolean {
        return tokens.isEmpty() || (tokens.size == 1 && tokens[0].getType() == token.DataType.LEFT_BRACKET)
    }

    override fun build(tokens: List<Token>): AST {
        return astn.EmptyAST()
    }
}
