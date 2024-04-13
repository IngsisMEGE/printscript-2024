package impl

import astBuilders.BuilderProvider
import astn.AST
import astn.EmptyAST
import exceptions.SyntacticError
import interfaces.Parser
import token.Token

class ParserImpl : Parser {
    private val builderProvider = BuilderProvider()

    override fun parse(input: List<Token>): AST {
        if (input.isEmpty()) return EmptyAST()

        val builders = builderProvider.getBuilderList()

        for (builder in builders) {
            if (builder.isValid(input)) return builder.build(input)
        }

        throw SyntacticError("Unexpected structure")
    }
}
