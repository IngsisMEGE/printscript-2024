package lexer

import token.Token


class ListTokenManager {

    companion object{
        fun orderTokens(list : List<Token>) : List<Token> {
            return list.sortedWith(compareBy({ it.getInitialPosition().first }, { it.getInitialPosition().second }, { it.getFinalPosition().first }, { it.getFinalPosition().second }))
        }

        //Falta que no elimine el (a) cuando hago un methodCall
        fun removeTokenFromString(line : String, token : Token) : String {
            val initialPosition = token.getInitialPosition()
            val finalPosition = token.getFinalPosition()
            val firstPart = line.substring(0, initialPosition.first)
            val secondPart = line.substring(finalPosition.first + 1, line.length)
            return firstPart + secondPart
        }
    }
}