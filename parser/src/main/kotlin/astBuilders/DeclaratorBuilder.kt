package astBuilders

import astBuilders.AstBuilder.Companion.checkMaxLength
import astBuilders.AstBuilder.Companion.checkMinLength
import astBuilders.AstBuilder.Companion.checkTokenType
import astBuilders.AstBuilder.Companion.takeCommentsAndSemiColon
import astn.AST
import astn.VarDeclaration
import token.DataType
import token.Token

/**
 * This class is responsible for building variable declarations in the PrintScript application.
 * It implements the AstBuilder interface and overrides the isValid and build functions.
 *
 * The isValid function takes a list of tokens as input and checks if they represent a valid variable declaration. It removes comments and semicolons from the list, and then checks if the first token is a DECLARATION_VARIABLE token and if the third token is a DOUBLE_DOTS token.
 *
 * The build function takes a list of tokens as input and builds a VarDeclaration AST from them. It first removes comments and semicolons from the list, then verifies the structure of the tokens, and finally creates a VarDeclaration object with the fourth token as the type and the second token as the variable name.
 *
 * The verifyStructure function checks the structure of a list of tokens. It checks if the list contains exactly four tokens, if the first token is a DECLARATION_VARIABLE token, if the second token is a VARIABLE_NAME token, if the third token is a DOUBLE_DOTS token, and if the fourth token is a NUMBER_TYPE or STRING_TYPE token.
 *
 */

class DeclaratorBuilder : AstBuilder {
    override fun isValid(tokens: List<Token>): Boolean {
        val parsedTokens = takeCommentsAndSemiColon(tokens)
        if (parsedTokens.size < 4) return false
        if (parsedTokens[0].getType() != DataType.DECLARATION_VARIABLE) return false
        if (parsedTokens[1].getType() != DataType.VARIABLE_NAME) return false
        if (parsedTokens[2].getType() != DataType.DOUBLE_DOTS) return false
        return true
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
