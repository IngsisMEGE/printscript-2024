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
        val interpreter = InterpreterImpl()
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
        val interpreter = InterpreterImpl()
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
        val interpreter = InterpreterImpl()

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
        val interpreter = InterpreterImpl()
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
        val interpreter = InterpreterImpl()
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
        assertEquals("Variable Already Exists at Line 0", exception.message)
    }

    @Test
    fun test006DeclareVariableWithValueTwoTimesShouldError() {
        val interpreter = InterpreterImpl()
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
        assertEquals("Variable Already Exists at Line 0", exception.message)
    }

    @Test
    fun test007PrintScriptWithVariable() {
        val interpreter = InterpreterImpl()
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
        val interpreter = InterpreterImpl()
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
        val interpreter = InterpreterImpl()
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
        val interpreter = InterpreterImpl()
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
        val interpreter = InterpreterImpl()
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
}
