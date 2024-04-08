package org.example.lexer

import lexer.TokenRegexRule
import org.example.lexer.token.MethodCallRule
import token.DataType
import token.Token

/**
 * The `Lexer` class is responsible for converting a sequence of characters into a sequence of tokens.
 * It analyzes the input source code to identify lexical components such as keywords, identifiers, literals, and operators.
 *
 * @param rules A list of token generation rules that define how to recognize different tokens.
 */
class Lexer(private val tokenRules: Map<String, TokenRegexRule> = mapOf()) : LexerInterface {
    private var tokenGenerator: List<RegexTokenGenerator> =
        tokenRules.map { (_, rule) ->
            if (rule.getType() == DataType.METHOD_CALL) {
                RegexTokenGenerator(rule, MethodCallRule())
            } else {
                RegexTokenGenerator(rule)
            }
        }

    private var codeFraction: List<String> = listOf()

    override fun lex(
        line: String,
        numberLine: Int,
    ): List<Token> {
        if (line.isBlank()) return emptyList()
        if (codeFraction.isEmpty()) {
            codeFraction = getCodeFraction(line)
        }
        val tokens = mutableListOf<Token>()
        tokenGenerator.forEach { tokenGenerator ->
            val generatedTokens = tokenGenerator.generateToken(codeFraction.first(), numberLine)
            if (generatedTokens.isNotEmpty()) {
                tokens.addAll(generatedTokens)
            }
        }

        if (tokens.isEmpty()) {
            tokens.add(Token(DataType.ERROR, codeFraction.first(), Pair(0, numberLine), Pair(codeFraction.first().length - 1, numberLine)))
        }

        codeFraction = codeFraction.drop(1)
        return ListTokenManager.removeOverlapTokens(ListTokenManager.orderTokens(tokens))
    }

    override fun isLineFinished(): Boolean {
        return codeFraction.isEmpty()
    }

    private fun getCodeFraction(line: String): List<String> {
        val codeFraction: MutableList<String> = mutableListOf()

        val semiColonTokens = getSemiColonTokens(line)

        if (semiColonTokens.isNotEmpty()) {
            var startPos = 0
            semiColonTokens.forEach { token ->
                val endPos = token.getFinalPosition().first + 1
                codeFraction.add(line.substring(startPos, endPos).trim())
                startPos = endPos
            }
            if (startPos < line.length) {
                codeFraction.add(line.substring(startPos).trim())
            }
        } else {
            codeFraction.add(line)
        }

        return codeFraction
    }

    private fun getSemiColonTokens(line: String): List<Token> {
        val semiColonTokenType = tokenRules.filterValues { it.getType() == DataType.SEMICOLON }.keys.firstOrNull()
        return if (semiColonTokenType != null) {
            val semiColonRule = tokenRules[semiColonTokenType]!!
            val semiColonGenerator = RegexTokenGenerator(semiColonRule)
            semiColonGenerator.generateToken(line, 0)
        } else {
            emptyList()
        }
    }
}
