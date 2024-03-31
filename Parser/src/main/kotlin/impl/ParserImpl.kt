package impl

import astBuilders.BuilderProvider
import astn.AST
import exceptions.ParsinException
import exceptions.SyntacticError
import interfaces.Parser
import token.Token

class ParserImpl : Parser {
    private val builderProvider = BuilderProvider()

    override fun parse(input: List<Token>): AST {
        if (input.isEmpty()) throw ParsinException("Empty input")

        val builders = builderProvider.getBuilderList()

        for (builder in builders) {
            if (builder.isValid(input)) return builder.build(input)
        }

        throw SyntacticError("Unexpected structure")
    }
}
