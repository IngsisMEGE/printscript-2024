package astn

import token.Token

/**
 * This interface represents the Abstract Syntax Tree (AST) for the PrintScript application.
 * It is implemented by various classes that represent different types of nodes in the AST.
 */
interface AST

/**
 * This data class represents a variable declaration node in the AST.
 *
 * @property type The token representing the type of the variable.
 * @property assignation The token representing the assignation of the variable.
 */
data class VarDeclaration(val type: Token, val assignation: Token) : AST

/**
 * This data class represents a variable declaration with assignation node in the AST.
 *
 * @property varDeclaration The VarDeclaration object representing the variable declaration.
 * @property value The OpTree object representing the value assigned to the variable.
 */
data class VarDeclarationAssignation(val varDeclaration: VarDeclaration, val value: OpTree) : AST

/**
 * This data class represents an assignation node in the AST.
 *
 * @property assignation The token representing the assignation.
 * @property value The OpTree object representing the value assigned.
 */
data class Assignation(val assignation: Token, val value: OpTree) : AST

/**
 * This data class represents a method node in the AST.
 *
 * @property methodName The token representing the method name.
 * @property value The OpTree object representing the value of the method.
 */
data class Method(val methodName: Token, val value: OpTree) : AST

/**
 * This interface represents an operation tree node in the AST.
 * It is implemented by various classes that represent different types of operations.
 */
interface OpTree

/**
 * This data class represents an operation head node in the operation tree.
 *
 * @property operator The token representing the operator.
 * @property left The OpTree object representing the left operand.
 * @property right The OpTree object representing the right operand.
 */
data class OperationHead(val operator: Token, val left: OpTree, val right: OpTree) : OpTree

/**
 * This data class represents a number operation node in the operation tree.
 *
 * @property value The token representing the number value.
 */
data class OperationNumber(val value: Token) : OpTree

/**
 * This data class represents a string operation node in the operation tree.
 *
 * @property value The token representing the string value.
 */
data class OperationString(val value: Token) : OpTree

/**
 * This data class represents a variable operation node in the operation tree.
 *
 * @property value The token representing the variable value.
 */
data class OperationVariable(val value: Token) : OpTree
