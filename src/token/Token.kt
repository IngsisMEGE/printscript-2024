package token

class Token(private val type: DataType, private val value: String?,private val initialPosition: Pair<Int,Int>,private val finalPosition: Pair<Int,Int>) {
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
    fun getType(): DataType{ return type }
    fun getInitialPosition(): Pair<Int,Int>{return initialPosition }

    fun getFinalPosition(): Pair<Int,Int>{return finalPosition }
}
/*

 */
enum class DataType{
    NUMBER_KEYWORD,
    STRING_KEYWORD,
    NUMBER_VALUE,
    STRING_VALUE,
    VARIABLE_NAME,
    LET_KEYWORD,
    OPERATOR_PLUS,
    OPERATOR_MINUS,
    OPERATOR_MULTIPLY,
    OPERATOR_DIVIDE,
    DOUBLE_DOTS,
    SEMICOLON,
    ASIGNATION_EQUALS,
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS,
    METHOD_CALL,
    COMA,
    WHITE_SPACE,
    SINGLE_LINE_COMMENT,
    MULTI_LINE_COMMENT_START,
    MULTI_LINE_COMMENT_END,
    ERROR,
    UNKNOWN
}