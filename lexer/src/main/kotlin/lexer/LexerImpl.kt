package lexer

import lexer.tokenRule.StringRule
import org.example.lexer.Lexer
import org.example.lexer.ListTokenManager
import org.example.lexer.RegexTokenGenerator
import org.example.lexer.token.MethodCallRule
import token.DataType
import token.Token
import javax.xml.crypto.Data

/**
 * A lexer that tokenizes source code based on a set of regular expression rules.
 *
 * @param tokenRules A map of regular expression rules used to generate tokens.
 */
class LexerImpl(private val tokenRules: Map<String, TokenRegexRule> = mapOf()) : Lexer {
    private var tokenGenerator: List<RegexTokenGenerator> =
        tokenRules.map { (_, rule) ->
            if (rule.getType() == DataType.METHOD_CALL) {
                RegexTokenGenerator(rule, MethodCallRule())
            } else if (rule.getType() == DataType.STRING_VALUE) {
                RegexTokenGenerator(rule, StringRule())
            } else {
                RegexTokenGenerator(rule)
            }
        }

    private val separatorTokens = listOf(DataType.SEPARATOR, DataType.LEFT_BRACKET, DataType.ELSE_STATEMENT , DataType.RIGHT_BRACKET)

    private var codeFraction: List<String> = listOf()

    private var tokens: List<Token> = listOf()

    override fun isLineFinished(): Boolean {
        return codeFraction.isEmpty()
    }

    override fun lex(
        line: String,
        numberLine: Int,
    ): List<Token> {
        if (isLineEmpty(line)) return emptyList()
        tokens += ListTokenManager.orderAndRemoveOverlapTokens(generateTokens(numberLine))

        codeFraction = codeFraction.drop(1)
        if (isLastTokenSeparator()) {
            val tempTokens = tokens
            tokens = listOf()
            return tempTokens
        }
        return listOf()
    }

    private fun isLastTokenSeparator() = tokens.isNotEmpty() && separatorTokens.contains(tokens.last().getType())

    private fun isLineEmpty(line: String): Boolean {
        if (line.isBlank()) return true
        if (codeFraction.isEmpty()) {
            codeFraction = getCodeFraction(line)
        }
        return false
    }

    private fun generateTokens(numberLine: Int): MutableList<Token> {
        val tokens = mutableListOf<Token>()
        tokenGenerator.forEach { tokenGenerator ->
            if (!tokenGenerator.doesItMatch(codeFraction.first())) return@forEach
            val generatedTokens = tokenGenerator.generateToken(codeFraction.first(), numberLine)
            if (generatedTokens.isNotEmpty()) {
                tokens.addAll(generatedTokens)
            }
        }
        if (tokens.isEmpty()) tokens.add(generateErrorToken(numberLine))
        return tokens
    }

    private fun generateErrorToken(numberLine: Int) =
        Token(
            DataType.ERROR,
            codeFraction.first(),
            Pair(0, numberLine),
            Pair(codeFraction.first().length - 1, numberLine),
        )

    private fun getCodeFraction(line: String): List<String> {
        val codeFraction: MutableList<String> = mutableListOf()

        val separatorTokens = getSeparatorTokens(line, separatorTokens)

        if (separatorTokens.isNotEmpty()) {
            separateLineInSegments(separatorTokens, codeFraction, line)
        } else {
            codeFraction.add(line)
        }

        return codeFraction
    }

    private fun separateLineInSegments(
        separatorTokens: List<Token>,
        codeFraction: MutableList<String>,
        line: String,
    ) {
        var startPos = 0
        separatorTokens.forEach { token ->
            val endPos = token.getFinalPosition().first + 1
            codeFraction.add(line.substring(startPos, endPos))
            startPos = endPos
        }
        if (startPos < line.length) {
            codeFraction.add(line.substring(startPos).trim())
        }
    }

    private fun getSeparatorTokens(line: String, separatorTypes : List<DataType> ): List<Token> {
        val matchingTokenTypes = tokenRules.filterValues { it.getType() in separatorTypes }
        val separatorTokens = mutableListOf<Token>()
        matchingTokenTypes.forEach { (_, rule) ->
            val generator = RegexTokenGenerator(rule)
            if (generator.doesItMatch(line)) {
                separatorTokens.addAll(generator.generateToken(line, 0))
            }
        }
        return ListTokenManager.orderAndRemoveOverlapTokens(separatorTokens)
    }
}
