import astn.OperationString
import org.junit.jupiter.api.Test
import rules.MethodRule
import rules.Rules
import token.DataType
import token.Token
import kotlin.test.assertEquals

class MethodRuleTest {
    @Test
    fun test001IsRuleIncluded() {
        val property: MutableMap<String, Any> = HashMap()
        property["ammountOfLines"] = 1
        val methodRule: Rules = MethodRule("ammountOfLines")

        val result = methodRule.isTheRuleIncluded(property)

        assert(result is MethodRule)
    }

    @Test
    fun test002EnforceRule() {
        var methodRule: Rules = MethodRule("ammountOfLines")
        val code = "println(\"Hello\")"
        methodRule = methodRule.isTheRuleIncluded(mapOf("ammountOfLines" to 1))
        val result = methodRule.enforceRule(code)

        assertEquals("\nprintln(\"Hello\")", result)
    }

    @Test
    fun test003GenericLine() {
        val ast =
            astn.Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationString(Token(DataType.STRING_VALUE, "\"Hello\"", Pair(7, 0), Pair(12, 0))),
            )
        val methodRule: Rules = MethodRule("ammountOfLines")

        val result = methodRule.genericLine(ast)

        assertEquals("println(\"Hello\")", result)
    }
}
