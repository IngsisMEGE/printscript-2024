import analyzers.SCAImpl
import astn.Assignation
import astn.Method
import astn.OperationHead
import astn.OperationInput
import astn.OperationString
import astn.VarDeclaration
import token.DataType
import token.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class SCATests {
    @Test
    fun camelCaseTest() {
        val rules = mapOf("CamelCaseFormat" to true)
        val sca = SCAImpl(rules)
        val declaration =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "snake_case", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val response = sca.readAst(declaration)
        assertEquals("Invalid typing format in line 0 row 0", response)
    }

    @Test
    fun camelCaseTest2() {
        val rules = mapOf("CamelCaseFormat" to true)
        val sca = SCAImpl(rules)
        val declaration =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "camelCase", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val response = sca.readAst(declaration)
        assertEquals("", response)
    }

    @Test
    fun snake_caseTest() {
        val rules = mapOf("SnakeCaseFormat" to true)
        val sca = SCAImpl(rules)
        val declaration =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "camelCase", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val response = sca.readAst(declaration)
        assertEquals("Invalid typing format in line 0 row 0", response)
    }

    @Test
    fun snake_caseTest2() {
        val rules = mapOf("SnakeCaseFormat" to true)
        val sca = SCAImpl(rules)
        val declaration =
            VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "snake_case", Pair(0, 0), Pair(1, 0)),
                true,
            )
        val response = sca.readAst(declaration)
        assertEquals("", response)
    }

    @Test
    fun methodCaseTest() {
        val rules = mapOf("MethodNoExpression" to true)
        val sca = SCAImpl(rules)
        val method =
            Method(
                Token(DataType.VARIABLE_NAME, "IAMNOTAMETHOD", Pair(0, 0), Pair(6, 0)),
                OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))),
            )
        val response = sca.readAst(method)
        assertEquals("", response)
    }

    @Test
    fun methodCaseTest2() {
        val rules = mapOf("MethodNoExpression" to true)
        val sca = SCAImpl(rules)
        val method =
            Method(
                Token(DataType.VARIABLE_NAME, "IAMNOTAMETHOD", Pair(0, 0), Pair(6, 0)),
                OperationHead(
                    Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                    OperationString(Token(DataType.STRING_VALUE, "hello ", Pair(0, 0), Pair(1, 0))),
                    OperationString(Token(DataType.STRING_VALUE, "world", Pair(0, 0), Pair(1, 0))),
                ),
            )
        val response = sca.readAst(method)
        assertEquals("Invalid operation in line 0, row 0", response)
    }

    @Test
    fun inputCaseTest() {
        val rules = mapOf("InputNoExpresion" to true)
        val sca = SCAImpl(rules)
        val method =
            Method(
                Token(DataType.VARIABLE_NAME, "IAMNOTAMETHOD", Pair(0, 0), Pair(6, 0)),
                OperationInput(
                    OperationString(Token(DataType.STRING_VALUE, "Hello", Pair(7, 0), Pair(12, 0))),
                ),
            )
        val response = sca.readAst(method)
        assertEquals("", response)
    }

    @Test
    fun inputCaseTest2() {
        val rules = mapOf("InputNoExpresion" to true)
        val sca = SCAImpl(rules)
        val method =
            Assignation(
                Token(DataType.VARIABLE_NAME, "IAMNOTAMETHOD", Pair(0, 0), Pair(6, 0)),
                OperationInput(
                    OperationHead(
                        Token(DataType.OPERATOR_PLUS, "+", Pair(0, 0), Pair(1, 0)),
                        OperationString(Token(DataType.STRING_VALUE, "hello ", Pair(0, 0), Pair(1, 0))),
                        OperationString(Token(DataType.STRING_VALUE, "world", Pair(0, 0), Pair(1, 0))),
                    ),
                ),
            )
        val response = sca.readAst(method)
        assertEquals("Invalid operation in line 0, row 0", response)
    }
}
