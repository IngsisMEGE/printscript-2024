package interfaces

import astn.AST
import token.Token

interface Parser {
    fun parse(input: List<Token>): AST
}
