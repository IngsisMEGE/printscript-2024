import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import rules.IfRule
import token.DataType
import token.Token

class IfRuleTest {
    @Test
    fun test001genericLine() {
        val ast =
            astn.IfStatement(
                astn.OperationBoolean(
                    Token(DataType.BOOLEAN_VALUE, "true", Pair(0, 0), Pair(2, 0)),
                ),
            )
        val ifRule = IfRule()
        val result = ifRule.genericLine(ast)
        assertEquals("if(true){", result)
    }

    @Test
    fun test002enforceRule() {
        val ifRule = IfRule()
        val code = "if(true){"
        val assignationRule = ifRule.generateEnforcers(mapOf())
        val result = assignationRule.enforceRule(code)
        assertEquals("if(true) {", result)
    }

    @Test
    fun test003canCreateGenericLine() {
        val ifRule = IfRule()
        val result =
            ifRule.canCreateGenericLine(
                astn.IfStatement(
                    astn.OperationBoolean(
                        Token(
                            DataType.BOOLEAN_VALUE,
                            "true",
                            Pair(0, 0),
                            Pair(2, 0),
                        ),
                    ),
                ),
            )
        assertEquals(true, result)
    }
}
