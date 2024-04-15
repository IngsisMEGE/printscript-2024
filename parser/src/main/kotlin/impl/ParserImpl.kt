package impl

import astBuilders.BuilderProvider
import astn.AST
import exceptions.SyntacticError
import interfaces.Parser
import token.Token

class ParserImpl : Parser {
    private val builderProvider = BuilderProvider()

    override fun parse(input: List<Token>): AST {
        val builders = builderProvider.getBuilderList()

        for (builder in builders) {
            if (builder.isValid(input)) return builder.build(input)
        }

        throw SyntacticError("Unexpected structure at Line ${input.first().getInitialPosition().second}")
    }
}
