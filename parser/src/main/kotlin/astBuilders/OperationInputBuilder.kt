package astBuilders

import astn.OperationInput
import token.DataType
import token.Token

class OperationInputBuilder {
    fun canCreate(tokens: List<Token>): Boolean {
        return tokens[0].getType() == DataType.METHOD_CALL && tokens[1].getType() == DataType.LEFT_PARENTHESIS &&
            tokens.last().getType() == DataType.RIGHT_PARENTHESIS
    }

    fun createInputOperation(tokens: List<Token>): OperationInput {
        return OperationInput(OperationBuilder().buildOperation(tokens.subList(2, tokens.size - 1)))
    }
}
