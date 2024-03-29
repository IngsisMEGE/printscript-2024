import ASTN.OperationNumber
import ASTN.OperationString
import ASTN.VarDeclaration
import ASTN.VarDeclarationAssignation
import Interpreter.RegularInterpreter
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.assertEquals

class InterpreterTest {

    @Test
    fun test001RegularInterpreterTestVarDeclaration() {
        val interpreter = RegularInterpreter()
        val ast = ASTN.VarDeclaration(Token(DataType.NUMBER_TYPE, "number", Pair(4,0) , Pair(5,0)), Token(DataType.VARIABLE_NAME, "x", Pair(0,0), Pair(1,0)))
        val result = interpreter.readAST(ast)
        assertEquals("", result)
    }

    @Test
    fun test002RegularInterpreterTestVarDeclarationAssignation() {
        val interpreter = RegularInterpreter()
        val ast: VarDeclarationAssignation = VarDeclarationAssignation(
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(0, 0), Pair(4, 0)),
                Token(DataType.VARIABLE_NAME, "dong", Pair(5, 0), Pair(8, 0))
            ), OperationString(Token(DataType.STRING_VALUE, "Hola", Pair(12, 0), Pair(15, 0)))
        )

        val exception = assertThrows<Exception> {
            interpreter.readAST(ast)
        }

        assertEquals("mal", exception.message)
    }

    @Test
    fun test003RegularInterpreterAssignation() {
        val interpreter = RegularInterpreter()
        val ast = ASTN.Assignation(
            Token(DataType.VARIABLE_NAME, "a", Pair(0,0), Pair(1,0)),
            OperationNumber(
                Token(DataType.NUMBER_TYPE, "5", Pair(2,0), Pair(3,0))
            )
        )

        val exception = assertThrows<Exception> {
            interpreter.readAST(ast)
        }

        assertEquals("mal", exception.message)
    }

    @Test
    fun test004RegularInterpreterMethod() {
        val interpreter = RegularInterpreter()
        val ast = ASTN.Method(Token(DataType.VARIABLE_NAME, "println", Pair(0,0), Pair(6,0)), OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7,0), Pair(12,0))))
        val result = interpreter.readAST(ast)
        assertEquals("Hello", result)
    }
}