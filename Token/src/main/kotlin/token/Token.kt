package token

/**
 * Represents a lexical token in the source code. Tokens are the smallest units of meaning recognized by the lexer.
 *
 * @property type The type of the token, which indicates its role in the language (e.g., keyword, identifier).
 * @property value The string value of the token, which is the exact text matched in the source code.
 * @property initialPosition The starting position of the token in the source code, usually represented as a line and column number.
 * @property finalPosition The ending position of the token in the source code.
 */
class Token(private val type: DataType, private val value: String?, private val initialPosition: Pair<Int,Int>, private val finalPosition: Pair<Int,Int>) {
    init {
        if (initialPosition.second > finalPosition.second) {
            throw IllegalArgumentException("The initial position must be less than the final position")
        }

        if (initialPosition.second == finalPosition.second && initialPosition.first > finalPosition.first) {
            throw IllegalArgumentException("The initial position must be less than the final position")
        }
    }
    fun getValue(): String {
        if(value == null) {
            return ""
        }
        return value
    }
    fun getType(): DataType { return type }
    fun getInitialPosition(): Pair<Int,Int>{return initialPosition }

    fun getFinalPosition(): Pair<Int,Int>{return finalPosition }
}
/*

 */
enum class DataType{
    NUMBER_TYPE,
    STRING_TYPE,
    NUMBER_VALUE,
    STRING_VALUE,
    VARIABLE_NAME,
    DECLARATION_VARIABLE,
    OPERATOR_PLUS,
    OPERATOR_MINUS,
    OPERATOR_MULTIPLY,
    OPERATOR_DIVIDE,
    DOUBLE_DOTS,
    SEMICOLON,
    ASSIGNATION,
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS,
    METHOD_CALL,
    MODULUS,
    COMA,
    WHITE_SPACE,
    SINGLE_LINE_COMMENT,
    MULTI_LINE_COMMENT_START,
    MULTI_LINE_COMMENT_END,
    ERROR,
    UNKNOWN
}