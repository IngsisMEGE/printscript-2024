import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import rules.ElseRule

class ElseRuleTest {
    @Test
    fun test001genericLineIsElseFalse() {
        val ast =
            astn.CloseIfStatement(
                isElse = false,
            )
        val elseRule = ElseRule()
        val result = elseRule.genericLine(ast)
        assertEquals("}", result)
    }

    @Test
    fun test002genericLineIsElseTrue() {
        val ast =
            astn.CloseIfStatement(
                isElse = true,
            )
        val elseRule = ElseRule()
        val result = elseRule.genericLine(ast)
        assertEquals("}else{", result)
    }

    @Test
    fun test003enforceRuleIsElseFalse() {
        val elseRule = ElseRule()
        val code = "}"
        val assignationRule = elseRule.isTheRuleIncluded(mapOf())
        val result = assignationRule.enforceRule(code)
        assertEquals("}", result)
    }

    @Test
    fun test004enforceRuleIsElseTrue() {
        val elseRule = ElseRule()
        val code = "}else{"
        val assignationRule = elseRule.isTheRuleIncluded(mapOf())
        val result = assignationRule.enforceRule(code)
        assertEquals("}else {", result)
    }
}
