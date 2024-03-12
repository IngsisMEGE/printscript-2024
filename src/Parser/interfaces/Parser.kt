package Parser.interfaces

import ASTN.AST
import Token.Token

interface Parser {
    fun parse(input: List<Token>): AST
}