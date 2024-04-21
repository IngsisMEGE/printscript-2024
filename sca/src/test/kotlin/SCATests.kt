import analyzers.SCAImpl
import astn.Assignation
import astn.Method
import astn.OperationHead
import astn.OperationInput
import astn.OperationString
import astn.VarDeclaration
import org.junit.jupiter.api.assertThrows
import token.DataType
import token.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class SCATests {
    @Test
    fun test001_camelCaseTest() {
        val rules =
            mapOf(
                "CamelCaseFormat" to true,
                "SnakeCaseFormat" to false,
                "MethodNoExpression" to false,
                "InputNoExpression" to false,
            )
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
    fun test002_camelCaseTest2() {
        val rules =
            mapOf(
                "CamelCaseFormat" to true,
                "SnakeCaseFormat" to false,
                "MethodNoExpression" to false,
                "InputNoExpression" to false,
            )
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
    fun test003_snake_caseTest() {
        val rules =
            mapOf(
                "SnakeCaseFormat" to true,
                "CamelCaseFormat" to false,
                "MethodNoExpression" to false,
                "InputNoExpression" to false,
            )
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
    fun test004_snake_caseTest2() {
        val rules =
            mapOf(
                "SnakeCaseFormat" to true,
                "CamelCaseFormat" to false,
                "MethodNoExpression" to false,
                "InputNoExpression" to false,
            )
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
    fun test005_methodCaseTest() {
        val rules =
            mapOf(
                "MethodNoExpression" to true,
                "InputNoExpression" to false,
                "CamelCaseFormat" to false,
                "SnakeCaseFormat" to false,
            )
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
    fun test006_methodCaseTest2() {
        val rules =
            mapOf(
                "MethodNoExpression" to true,
                "InputNoExpression" to false,
                "CamelCaseFormat" to false,
                "SnakeCaseFormat" to false,
            )
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
    fun test007_inputCaseTest() {
        val rules =
            mapOf(
                "InputNoExpression" to true,
                "MethodNoExpression" to false,
                "CamelCaseFormat" to false,
                "SnakeCaseFormat" to false,
            )
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
    fun test008_inputCaseTest2() {
        val rules =
            mapOf(
                "InputNoExpression" to true,
                "MethodNoExpression" to false,
                "CamelCaseFormat" to false,
                "SnakeCaseFormat" to false,
            )
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

    @Test
    fun test009_ifDontContainsAllProps() {
        val rules =
            mapOf(
                "InputNoExpression" to true,
                "MethodNoExpression" to false,
                "CamelCaseFormat" to false,
            )
        assertThrows<IllegalArgumentException> {
            SCAImpl(rules)
        }
    }

    @Test
    fun test010_ifBothCamelCaseAndSnakeCaseAreTrue() {
        val rules =
            mapOf(
                "InputNoExpression" to true,
                "MethodNoExpression" to false,
                "CamelCaseFormat" to true,
                "SnakeCaseFormat" to true,
            )
        assertThrows<IllegalArgumentException> {
            SCAImpl(rules)
        }
    }
}
