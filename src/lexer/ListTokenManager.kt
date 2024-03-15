package lexer

import token.Token


class ListTokenManager {

    companion object{
        fun orderTokens(list : List<Token>) : List<Token> {
            // Order by initial position and final position
            return list.sortedWith(compareBy({ it.getInitialPosition().first }, { it.getInitialPosition().second }, { it.getFinalPosition().first }, { it.getFinalPosition().second }))
        }
    }
}