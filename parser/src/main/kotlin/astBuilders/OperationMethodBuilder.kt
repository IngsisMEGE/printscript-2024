package astBuilders

import astn.OperationMethod
import astn.OperationString
import token.DataType
import token.Token

class OperationMethodBuilder {
    fun canCreate(tokens: List<Token>): Boolean {
        return tokens[0].getType() == DataType.METHOD_CALL && tokens[1].getType() == DataType.LEFT_PARENTHESIS &&
            tokens.last().getType() == DataType.RIGHT_PARENTHESIS
    }

    fun createInputOperation(tokens: List<Token>): OperationMethod {
        if (tokens.subList(
                2,
                tokens.size - 1,
            ).isEmpty()
        ) {
            return OperationMethod(tokens.first(), OperationString(Token(DataType.STRING_VALUE, "", Pair(0, 0), Pair(0, 0))))
        }
        return OperationMethod(tokens.first(), OperationBuilder().buildOperation(tokens.subList(2, tokens.size - 1)))
    }
}
