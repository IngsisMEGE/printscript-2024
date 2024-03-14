package Token

class TokenTest

fun tokenShouldHaveAValidType() {
    val token = Token(DataType.SEMICOLON, "a", Pair(0,0),1)
    assert(token.type == DataType.SEMICOLON)
}

fun tokenShouldHaveAValidValue() {
    val token = Token(DataType.SEMICOLON, ";", Pair(0,0),1)
    assert(token.value == ";")
}
fun tokenShouldBeValid() {
    val token = Token(DataType.SEMICOLON, ";", Pair(0,0),1)
    assert(token.type == DataType.SEMICOLON)
    assert(token.value == ";")
}

