import kotlin.test.Test

class AssignationExecutionTest {
    @Test
    fun test001AssignationExecutorVariableNotFound() {
//        val assignationExecutor = AssignationExecution()
//        val ast = Assignation(
//            Token(DataType.VARIABLE_NAME, "IAMNOTAVARIABLE", Pair(0, 0), Pair(6, 0)),
//            OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0)))
//        )
//        val exception = assertThrows<Exception> {
//            assignationExecutor.execute(ast, mutableMapOf())
//        }
//        assertEquals("Variable not found", exception.message)
    }

    @Test
    fun test002AssignationExecutorVariableTypeMismatch() {
//        val valDeclarationExecutor = DeclarationExecution()
//        val assignationExecutor = AssignationExecution()
//        val map = mutableMapOf<String, Value>()
//
//        val ast1 = VarDeclaration(
//            Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
//            Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
//        )
//        val ast2 = Assignation(
//            Token(DataType.VARIABLE_NAME, "IAMNOTAVARIABLE", Pair(0, 0), Pair(6, 0)),
//            OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0)))
//        )
//        valDeclarationExecutor.execute(ast1, map)
//        val exception = assertThrows<Exception> {
//            assignationExecutor.execute(ast2, map)
//        }
//        assertEquals("Variable type mismatch", exception.message)
    }

    @Test
    fun test003AssignationExecutorShouldAssignCorrectly() {
//        val valDeclarationExecutor = DeclarationExecution()
//        val assignationExecutor = AssignationExecution()
//        val map = mutableMapOf<String, Value>()
//
//        val ast1 = VarDeclaration(
//            Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
//            Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
//        )
//        val ast2 = Assignation(
//            Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
//            OperationNumber(Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0))
//        ))
//        valDeclarationExecutor.execute(ast1, map)
//        val result = assignationExecutor.execute(ast2, map)
//        assertEquals("", result)
    }

    @Test
    fun test004AssignationExecutorShouldAssignCorrectlyWithStringType() {
//        val valDeclarationExecutor = DeclarationExecution()
//        val assignationExecutor = AssignationExecution()
//        val map = mutableMapOf<String, Value>()
//
//        val ast1 = VarDeclaration(
//            Token(DataType.STRING_TYPE, "string", Pair(4, 0), Pair(5, 0)),
//            Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
//        )
//        val ast2 = Assignation(
//            Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
//            OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))
//        ))
//        valDeclarationExecutor.execute(ast1, map)
//        val result = assignationExecutor.execute(ast2, map)
//        assertEquals("", result)
    }

    @Test
    fun test005AssignationExecutorShouldAssignCorrectlyAComplexExpression() {
//        val valDeclarationExecutor = DeclarationExecution()
//        val assignationExecutor = AssignationExecution()
//        val map = mutableMapOf<String, Value>()
//        val ast1 = VarDeclaration(
//            Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
//            Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
//        )
//        val ast2 = Assignation(
//            Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
//            OperationHead(Token(DataType.OPERATOR_PLUS , "+", Pair(7, 0), Pair(12, 0)),
//                OperationNumber(Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0))),
//                OperationNumber(Token(DataType.NUMBER_VALUE, "2", Pair(7, 0), Pair(12, 0)))
//        ))
//        valDeclarationExecutor.execute(ast1, map)
//        val result = assignationExecutor.execute(ast2, map)
//        assertEquals("", result)
    }

    @Test
    fun test006AssignationExecutorShouldConcatenate2String() {
//        val valDeclarationExecutor = DeclarationExecution()
//        val assignationExecutor = AssignationExecution()
//        val map = mutableMapOf<String, Value>()
//        val ast1 = VarDeclaration(
//            Token(DataType.STRING_TYPE, "string", Pair(4, 0), Pair(5, 0)),
//            Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
//        )
//        val ast2 = Assignation(
//            Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
//            OperationHead(Token(DataType.OPERATOR_PLUS , "+", Pair(7, 0), Pair(12, 0)),
//                OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))),
//                OperationString(Token(DataType.STRING_VALUE, "World", Pair(7, 0), Pair(12, 0)))
//        ))
//
//        valDeclarationExecutor.execute(ast1, map)
//        val result = assignationExecutor.execute(ast2, map)
//        assertEquals("", result)
    }

    @Test
    fun test007AssignationExecutorShouldWorkWithVarDeclarationAssignation() {
//        val valDeclarationAssignation = DeclarationAssignationExecution()
//        val assignationExecutor = AssignationExecution()
//        val map = mutableMapOf<String, Value>()
//        val ast1 = VarDeclarationAssignation(
//            VarDeclaration(
//                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
//                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
//            ),
//            OperationNumber(Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0))
//        ))
//
//        val ast2 = Assignation(
//            Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
//            OperationNumber(Token(DataType.NUMBER_VALUE, "4", Pair(7, 0), Pair(12, 0))
//        ))
//
//        valDeclarationAssignation.execute(ast1, map)
//
//        val result = assignationExecutor.execute(ast2, map)
//        assertEquals("", result)
    }
}
