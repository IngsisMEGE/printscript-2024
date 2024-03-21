package Parser.impl

import ASTN.AST
import Parser.ASTBuilders.BuilderProvider
import Parser.exceptions.ParsinException
import Parser.exceptions.SyntacticError
import Parser.exceptions.UnexpectedTokenException
import Parser.interfaces.Parser
import Token.Token

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