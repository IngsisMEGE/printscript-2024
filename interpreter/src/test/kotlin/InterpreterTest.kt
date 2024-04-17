import astn.Assignation
import astn.Method
import astn.OperationBoolean
import astn.OperationNumber
import astn.OperationString
import astn.OperationVariable
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import interpreter.InterpreterImpl
import interpreter.Value
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.assertEquals

class InterpreterTest {
    @Test
    fun test001RegularInterpreterTestVarDeclaration() {
        val interpreter = InterpreterImpl(::input)
        val ast =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val variables = mutableMapOf<String, Value>()
        val result = interpreter.readAST(ast, variables)
        assertEquals("", result)
    }

    @Test
    fun test002RegularInterpreterTestVarDeclarationAssignation() {
        val interpreter = InterpreterImpl(::input)
        val ast =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(0, 0), Pair(4, 0)),
                    Token(DataType.VARIABLE_NAME, "dong", Pair(5, 0), Pair(8, 0)),
                    true,
                ),
                OperationString(Token(DataType.STRING_VALUE, "Hola", Pair(12, 0), Pair(15, 0))),
            )
        val variables = mutableMapOf<String, Value>()

        val exception =
            assertThrows<Exception> {
                interpreter.readAST(ast, variables)
            }

        assertEquals("Type Mismatch at Line 0 between NUMBER and STRING", exception.message)
    }

    @Test
    fun test003RegularInterpreterAssignation() {
        val interpreter = InterpreterImpl(::input)

        val ast2 =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(0, 0), Pair(4, 0)),
                Token(DataType.VARIABLE_NAME, "a", Pair(5, 0), Pair(6, 0)),
                true,
            )
        val variables = mutableMapOf<String, Value>()

        assertEquals("", interpreter.readAST(ast2, variables))
        // Cambialo al error que aparece
    }

    @Test
    fun test004RegularInterpreterMethod() {
        val interpreter = InterpreterImpl(::input)
        val ast =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))),
            )
        val variables = mutableMapOf<String, Value>()
        val result = interpreter.readAST(ast, variables)
        assertEquals("Hello\n", result)
    }

    @Test
    fun test005DeclareVariableTwoTimesShouldError() {
        val interpreter = InterpreterImpl(::input)
        val ast =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(0, 0), Pair(4, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(5, 0), Pair(6, 0)),
                true,
            )
        val ast2 =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(0, 0), Pair(4, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(5, 0), Pair(6, 0)),
                true,
            )
        val variables = mutableMapOf<String, Value>()

        interpreter.readAST(ast, variables)
        val exception =
            assertThrows<Exception> {
                interpreter.readAST(ast2, variables)
            }
        assertEquals("Variable 'x' already exists at Line 0", exception.message)
    }

    @Test
    fun test006DeclareVariableWithValueTwoTimesShouldError() {
        val interpreter = InterpreterImpl(::input)
        val ast =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(0, 0), Pair(4, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(5, 0), Pair(6, 0)),
                    true,
                ),
                OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(7, 0), Pair(8, 0))),
            )
        val ast2 =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(0, 0), Pair(4, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(5, 0), Pair(6, 0)),
                    true,
                ),
                OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(7, 0), Pair(8, 0))),
            )
        val variables = mutableMapOf<String, Value>()

        interpreter.readAST(ast, variables)
        val exception =
            assertThrows<Exception> {
                interpreter.readAST(ast2, variables)
            }
        assertEquals("Variable 'x' already exists at Line 0", exception.message)
    }

    @Test
    fun test007PrintScriptWithVariable() {
        val interpreter = InterpreterImpl(::input)
        val ast =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationVariable(Token(DataType.VARIABLE_NAME, "x", Pair(7, 0), Pair(8, 0))),
            )
        val ast2 =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(0, 0), Pair(4, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(5, 0), Pair(6, 0)),
                    true,
                ),
                OperationNumber(Token(DataType.NUMBER_VALUE, "5", Pair(7, 0), Pair(8, 0))),
            )
        val variables = mutableMapOf<String, Value>()

        interpreter.readAST(ast2, variables)
        val result = interpreter.readAST(ast, variables)
        assertEquals("5\n", result)
    }

    @Test
    fun test008DeclareBooleanVariableThenAssignNewValue() {
        val interpreter = InterpreterImpl(::input)
        val ast =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.BOOLEAN_TYPE, "boolean", Pair(0, 0), Pair(7, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(8, 0), Pair(9, 0)),
                    true,
                ),
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "true", Pair(10, 0), Pair(11, 0)),
                ),
            )

        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "false", Pair(7, 0), Pair(12, 0)),
                ),
            )

        val variables = mutableMapOf<String, Value>()

        interpreter.readAST(ast, variables)
        val result = interpreter.readAST(ast2, variables)
        assertEquals("", result)
    }

    @Test
    fun test009DeclareABooleanWithValueShouldError() {
        val interpreter = InterpreterImpl(::input)
        val ast =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.BOOLEAN_TYPE, "boolean", Pair(0, 0), Pair(7, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(8, 0), Pair(9, 0)),
                    true,
                ),
                OperationNumber(
                    Token(DataType.NUMBER_VALUE, "5", Pair(10, 0), Pair(11, 0)),
                ),
            )

        val variables = mutableMapOf<String, Value>()

        val exception =
            assertThrows<Exception> {
                interpreter.readAST(ast, variables)
            }
        assertEquals("Type Mismatch at Line 0 between BOOLEAN and NUMBER", exception.message)
    }

    @Test
    fun test010DeclareStringVariableThenAssignNewValue() {
        val interpreter = InterpreterImpl(::input)
        val ast =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.STRING_TYPE, "string", Pair(0, 0), Pair(7, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(8, 0), Pair(9, 0)),
                    true,
                ),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(10, 0), Pair(15, 0)),
                ),
            )

        val ast2 =
            Assignation(
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                OperationString(
                    Token(DataType.STRING_VALUE, "World", Pair(7, 0), Pair(12, 0)),
                ),
            )

        val variables = mutableMapOf<String, Value>()

        interpreter.readAST(ast, variables)
        val result = interpreter.readAST(ast2, variables)
        assertEquals("", result)
    }

    @Test
    fun test011DeclareBooleanWithValue() {
        val interpreter = InterpreterImpl(::input)
        val ast =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.BOOLEAN_TYPE, "boolean", Pair(0, 0), Pair(7, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(8, 0), Pair(9, 0)),
                    true,
                ),
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "true", Pair(10, 0), Pair(11, 0)),
                ),
            )

        val variables = mutableMapOf<String, Value>()

        val result = interpreter.readAST(ast, variables)
        assertEquals("", result)
    }

    private fun enterIfScope(storedVariables: MutableList<MutableMap<String, Value>>) {
        storedVariables.add(storedVariables.last().toMutableMap())
    }

    private fun mergeScopes(storedVariables: MutableList<MutableMap<String, Value>>) {
        val hasToUpdate = storedVariables[storedVariables.size - 2]
        val updated = storedVariables.last()

        for ((key, value) in updated) {
            if (hasToUpdate.containsKey(key)) {
                hasToUpdate[key] = value
            }
        }

        storedVariables.removeLast()
    }

    @Test
    fun test012IfStatementTrue() {
        val variables: MutableList<MutableMap<String, Value>> = mutableListOf(mutableMapOf())
        val interpreter = InterpreterImpl(::input, { enterIfScope(variables) }, { mergeScopes(variables) })
        val astDeclarationAssignation =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.BOOLEAN_TYPE, "boolean", Pair(0, 0), Pair(7, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(8, 0), Pair(9, 0)),
                    true,
                ),
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "true", Pair(10, 0), Pair(11, 0)),
                ),
            )

        val astIfStatement =
            astn.IfStatement(
                OperationVariable(
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                ),
            )
        interpreter.readAST(astDeclarationAssignation, variables.last())
        val result = interpreter.readAST(astIfStatement, variables.last())

        assertEquals("", result)
        assertEquals(variables.size, 2)
    }

    @Test
    fun test013IfStatementTrueShouldExecuteNextLines() {
        val variables: MutableList<MutableMap<String, Value>> = mutableListOf(mutableMapOf())
        val interpreter = InterpreterImpl(::input, { enterIfScope(variables) }, { mergeScopes(variables) })
        val astDeclarationAssignation =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.BOOLEAN_TYPE, "boolean", Pair(0, 0), Pair(7, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(8, 0), Pair(9, 0)),
                    true,
                ),
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "true", Pair(10, 0), Pair(11, 0)),
                ),
            )

        val astIfStatement =
            astn.IfStatement(
                OperationVariable(
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                ),
            )
        val astMethod =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0)),
                ),
            )
        interpreter.readAST(astDeclarationAssignation, variables.last())
        interpreter.readAST(astIfStatement, variables.last())
        val result = interpreter.readAST(astMethod, variables.last())

        assertEquals("Hello\n", result)
        assertEquals(variables.size, 2)
    }

    @Test
    fun test014IfStatementFalseShouldNotExecuteNextLines() {
        val variables: MutableList<MutableMap<String, Value>> = mutableListOf(mutableMapOf())
        val interpreter = InterpreterImpl(::input, { enterIfScope(variables) }, { mergeScopes(variables) })
        val astDeclarationAssignation =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.BOOLEAN_TYPE, "boolean", Pair(0, 0), Pair(7, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(8, 0), Pair(9, 0)),
                    true,
                ),
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "false", Pair(10, 0), Pair(11, 0)),
                ),
            )

        val astIfStatement =
            astn.IfStatement(
                OperationVariable(
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                ),
            )
        val astMethod =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0)),
                ),
            )
        interpreter.readAST(astDeclarationAssignation, variables.last())
        interpreter.readAST(astIfStatement, variables.last())
        val result = interpreter.readAST(astMethod, variables.last())

        assertEquals("", result)
        assertEquals(variables.size, 2)
    }

    @Test
    fun test015IfStatementTrueShouldMergeAssignationAfterClose() {
        val variables: MutableList<MutableMap<String, Value>> = mutableListOf(mutableMapOf())
        val interpreter = InterpreterImpl(::input, { enterIfScope(variables) }, { mergeScopes(variables) })
        val astDeclarationAssignation =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.BOOLEAN_TYPE, "boolean", Pair(0, 0), Pair(7, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(8, 0), Pair(9, 0)),
                    true,
                ),
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "true", Pair(10, 0), Pair(11, 0)),
                ),
            )
        val astDeclarationAssignation2 =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.STRING_TYPE, "string", Pair(0, 0), Pair(6, 0)),
                    Token(DataType.VARIABLE_NAME, "y", Pair(7, 0), Pair(8, 0)),
                    true,
                ),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(9, 0), Pair(14, 0)),
                ),
            )

        val astIfStatement =
            astn.IfStatement(
                OperationVariable(
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                ),
            )
        val astMethod =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0)),
                ),
            )
        val astAssignation =
            Assignation(
                Token(DataType.VARIABLE_NAME, "y", Pair(0, 0), Pair(1, 0)),
                OperationString(
                    Token(DataType.STRING_VALUE, "World", Pair(7, 0), Pair(12, 0)),
                ),
            )
        val astCloseIfStatement = astn.CloseIfStatement(false)
        val astMethod2 =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationVariable(
                    Token(DataType.VARIABLE_NAME, "y", Pair(7, 0), Pair(12, 0)),
                ),
            )

        interpreter.readAST(astDeclarationAssignation, variables.last())
        interpreter.readAST(astDeclarationAssignation2, variables.last())
        interpreter.readAST(astIfStatement, variables.last())
        val resul1 = interpreter.readAST(astMethod, variables.last())
        interpreter.readAST(astAssignation, variables.last())
        interpreter.readAST(astCloseIfStatement, variables.last())
        val result2 = interpreter.readAST(astMethod2, variables.last())

        assertEquals("Hello\n", resul1)
        assertEquals("World\n", result2)
        assertEquals(variables.size, 1)
        assertEquals(variables.last()["y"]!!.getValue(), "World")
    }

    @Test
    fun test016IfStatementFalseShouldEnterElse() {
        val variables: MutableList<MutableMap<String, Value>> = mutableListOf(mutableMapOf())
        val interpreter = InterpreterImpl(::input, { enterIfScope(variables) }, { mergeScopes(variables) })
        val astDeclarationAssignation =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.BOOLEAN_TYPE, "boolean", Pair(0, 0), Pair(7, 0)),
                    Token(DataType.VARIABLE_NAME, "x", Pair(8, 0), Pair(9, 0)),
                    true,
                ),
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "false", Pair(10, 0), Pair(11, 0)),
                ),
            )
        val astDeclarationAssignation2 =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.STRING_TYPE, "string", Pair(0, 0), Pair(6, 0)),
                    Token(DataType.VARIABLE_NAME, "y", Pair(7, 0), Pair(8, 0)),
                    true,
                ),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(9, 0), Pair(14, 0)),
                ),
            )

        val astIfStatement =
            astn.IfStatement(
                OperationVariable(
                    Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
                ),
            )
        val astMethod =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0)),
                ),
            )
        val astAssignation =
            Assignation(
                Token(DataType.VARIABLE_NAME, "y", Pair(0, 0), Pair(1, 0)),
                OperationString(
                    Token(DataType.STRING_VALUE, "World", Pair(7, 0), Pair(12, 0)),
                ),
            )
        val astElseIfStatement = astn.CloseIfStatement(true)
        val astMethod2 =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationVariable(
                    Token(DataType.VARIABLE_NAME, "y", Pair(7, 0), Pair(12, 0)),
                ),
            )
        val astCloseStatement = astn.CloseIfStatement(false)

        interpreter.readAST(astDeclarationAssignation, variables.last())
        interpreter.readAST(astDeclarationAssignation2, variables.last())
        interpreter.readAST(astIfStatement, variables.last())
        val resul1 = interpreter.readAST(astMethod, variables.last())
        interpreter.readAST(astAssignation, variables.last())
        interpreter.readAST(astElseIfStatement, variables.last())
        val result2 = interpreter.readAST(astMethod2, variables.last())
        interpreter.readAST(astCloseStatement, variables.last())

        assertEquals("", resul1)
        assertEquals("Hello\n", result2)
        assertEquals(variables.size, 1)
        assertEquals("Hello", variables.last()["y"]!!.getValue())
    }

    @Test
    fun test017IfInsideIfShouldMergeScopes() {
        val variables: MutableList<MutableMap<String, Value>> = mutableListOf(mutableMapOf())
        val interpreter = InterpreterImpl(::input, { enterIfScope(variables) }, { mergeScopes(variables) })
        val astDeclarationAssignation2 =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.STRING_TYPE, "string", Pair(0, 0), Pair(6, 0)),
                    Token(DataType.VARIABLE_NAME, "y", Pair(7, 0), Pair(8, 0)),
                    true,
                ),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(9, 0), Pair(14, 0)),
                ),
            )

        val astIfStatement =
            astn.IfStatement(
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "true", Pair(0, 0), Pair(1, 0)),
                ),
            )
        val astMethod =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationString(
                    Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0)),
                ),
            )
        val astAssignation =
            Assignation(
                Token(DataType.VARIABLE_NAME, "y", Pair(0, 0), Pair(1, 0)),
                OperationString(
                    Token(DataType.STRING_VALUE, "World", Pair(7, 0), Pair(12, 0)),
                ),
            )
        val astSecondIfStatement =
            astn.IfStatement(
                OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "true", Pair(0, 0), Pair(1, 0)),
                ),
            )

        val astDeclaration3 =
            VarDeclaration(
                Token(DataType.STRING_TYPE, "string", Pair(0, 0), Pair(6, 0)),
                Token(DataType.VARIABLE_NAME, "z", Pair(7, 0), Pair(8, 0)),
                true,
            )

        val astElseIfStatement = astn.CloseIfStatement(true)
        val astMethod2 =
            Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationVariable(
                    Token(DataType.VARIABLE_NAME, "y", Pair(7, 0), Pair(12, 0)),
                ),
            )

        val astCloseStatement = astn.CloseIfStatement(false)

        interpreter.readAST(astDeclarationAssignation2, variables.last())
        interpreter.readAST(astIfStatement, variables.last())
        val resul1 = interpreter.readAST(astMethod, variables.last())
        interpreter.readAST(astAssignation, variables.last())
        interpreter.readAST(astSecondIfStatement, variables.last())
        interpreter.readAST(astDeclaration3, variables.last())
        interpreter.readAST(astElseIfStatement, variables.last())
        val result2 = interpreter.readAST(astMethod2, variables.last())
        interpreter.readAST(astCloseStatement, variables.last())

        assertEquals("Hello\n", resul1)
        assertEquals("", result2)
        assertEquals(2, variables.size)
    }

    fun input(message: String): String {
        return message
    }
}
