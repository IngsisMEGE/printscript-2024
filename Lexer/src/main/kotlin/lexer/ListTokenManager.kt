package org.example.lexer

import token.Token

class ListTokenManager {
    companion object {
        fun orderTokens(list: List<Token>): List<Token> {
            return list.sortedWith(
                compareBy({
                    it.getInitialPosition().first
                }, { it.getInitialPosition().second }, { it.getFinalPosition().first }, { it.getFinalPosition().second }),
            )
        }

        fun removeOverlapTokens(tokens: List<Token>): List<Token> {
            val orderedTokens = orderTokens(tokens)
            val result = mutableListOf<Token>()
            var lastToken: Token? = null
            orderedTokens.forEach { token ->
                if (lastToken == null) {
                    result.add(token)
                    lastToken = token
                } else {
                    if (token.getInitialPosition().first > lastToken!!.getFinalPosition().first) {
                        result.add(token)
                        lastToken = token
                    } else if (token.getFinalPosition().first > lastToken!!.getFinalPosition().first) {
                        result.remove(lastToken)
                        result.add(token)
                        lastToken = token
                    }
                }
            }
            return result
        }
    }
}
