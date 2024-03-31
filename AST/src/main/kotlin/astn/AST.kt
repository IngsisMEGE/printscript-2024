package astn

import token.Token

interface AST

data class VarDeclaration(val type: Token, val assignation: Token) : AST

data class VarDeclarationAssignation(val varDeclaration: VarDeclaration, val value: OpTree) : AST

data class Assignation(val assignation: Token, val value: OpTree) : AST

data class Method(val methodName: Token, val value: OpTree) : AST

interface OpTree

data class OperationHead(val operator: Token, val left: OpTree, val right: OpTree) : OpTree

data class OperationNumber(val value: Token) : OpTree

data class OperationString(val value: Token) : OpTree

data class OperationVariable(val value: Token) : OpTree
