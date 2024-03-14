package Token

data class Token(val type: DataType, val value: String?, val location: Pair<Int,Int>, val length: Int) {

    fun value(): String
    {
        if(type == DataType.ASIGNATION_EQUALS || type == DataType.OPERATOR_PLUS || type == DataType.OPERATOR_MINUS || type == DataType.OPERATOR_MULTIPLY || type == DataType.OPERATOR_DIVIDE || type == DataType.DOUBLE_DOTS || type == DataType.SEMICOLON || type == DataType.LEFT_PARENTHESIS || type == DataType.RIGHT_PARENTHESIS || type == DataType.WHITE_SPACE || type == DataType.COMMENT)
        {
            return ""
        }
        return value!!
    }
}

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
    WHITE_SPACE,
    COMMENT,
    ERROR,
    UNKNOWN
}