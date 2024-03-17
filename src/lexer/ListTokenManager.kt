package lexer

import token.Token


class ListTokenManager {

    companion object{
        fun orderTokens(list : List<Token>) : List<Token> {
            return list.sortedWith(compareBy({ it.getInitialPosition().first }, { it.getInitialPosition().second }, { it.getFinalPosition().first }, { it.getFinalPosition().second }))
        }

        fun removeDuplicates(tokens: List<Token>): List<Token> {
            return tokens.distinctBy { it.getInitialPosition().first to it.getFinalPosition().first }
        }
    }
}