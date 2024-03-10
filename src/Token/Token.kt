package Token

data class Token(val type: DataType, val value: String, val location: Pair<Int,Int>, val length: Int) {

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
    OPERATOR_MULTiPLY,
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