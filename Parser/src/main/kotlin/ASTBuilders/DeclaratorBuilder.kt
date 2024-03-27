package Parser.ASTBuilders

import ASTN.AST
import ASTN.VarDeclaration
import Parser.ASTBuilders.AstBuilder.Companion.checkMaxLength
import Parser.ASTBuilders.AstBuilder.Companion.checkMinLength
import Parser.ASTBuilders.AstBuilder.Companion.checkTokenType
import Parser.ASTBuilders.AstBuilder.Companion.takeCommentsAndSemiColon
import token.DataType
import token.Token


class DeclaratorBuilder : AstBuilder {
    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        if (parsedTokens.size < 4) return false
        return parsedTokens[0].getType() == DataType.DECLARATION_VARIABLE || parsedTokens[2].getType() == DataType.DOUBLE_DOTS
    }

    override fun build(tokens: List<Token>): AST {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        verifyStructure(parsedTokens)
        return VarDeclaration(parsedTokens[3], parsedTokens[1])
    }

    private fun verifyStructure(tokens: List<Token>) {
        checkMinLength(tokens, 4, "declaration")
        checkMaxLength(tokens, 4, "declaration")
        checkTokenType(tokens[0], "Let or const", listOf(DataType.DECLARATION_VARIABLE))
        checkTokenType(tokens[1], "Identifier", listOf(DataType.VARIABLE_NAME))
        checkTokenType(tokens[2], "Double dots", listOf(DataType.DOUBLE_DOTS))
        checkTokenType(tokens[3], "Type", listOf(DataType.NUMBER_TYPE, DataType.STRING_TYPE))
    }
}