package lexer.tokenRule

import token.DataType
import token.Token


/**
 * Defines a rule for generating tokens. Implementations of this interface can specify custom logic
 * for identifying and creating tokens from portions of the source code.
 */
interface TokenRule {
    /**
     * Generates a token based on this rule, if applicable, for the given segment of the source code.
     *
     * @param type The type of token to generate.
     * @param value The string segment from the source code to analyze.
     * @param initialPosition The starting position of the segment in the source code.
     * @param finalPosition The ending position of the segment in the source code.
     * @return A Token object if the rule applies, or null if the segment does not match this rule.
     */
    fun generateToken(type: DataType, value: String, initialPosition: Pair<Int, Int>, finalPosition: Pair<Int, Int>): Token?

    /**
     * Returns a list of DataType values that this rule is capable of generating. This allows the lexer
     * to understand what types of tokens this rule can produce.
     *
     * @return A list of DataType enums.
     */
    fun getTypes(): List<DataType>
}