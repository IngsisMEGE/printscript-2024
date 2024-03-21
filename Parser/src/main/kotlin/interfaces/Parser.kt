package Parser.interfaces

import ASTN.AST
import token.Token

interface Parser {
    fun parse(input: List<Token>): AST
}