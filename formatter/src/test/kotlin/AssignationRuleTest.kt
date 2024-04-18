import astn.OperationNumber
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import rules.AssignationRule
import rules.Rules
import token.DataType
import token.Token

class AssignationRuleTest {
    @Test
    fun test001isRulValid() {
        val property: MutableMap<String, Any> = HashMap()
        property["EqualFront"] = 1
        property["EqualBack"] = 1
        val assignationRule: Rules = AssignationRule("EqualFront", "EqualBack")
        val result = assignationRule.isTheRuleIncluded(property)
        assert(result is AssignationRule)
    }

    @Test
    fun test002genericLine() {
        val ast =
            astn.Assignation(
                Token(DataType.VARIABLE_NAME, "a", Pair(0, 0), Pair(1, 0)),
                OperationNumber(
                    Token(DataType.NUMBER_TYPE, "5", Pair(2, 0), Pair(3, 0)),
                ),
            )
        val assignationRule: Rules = AssignationRule("EqualFront", "EqualBack")
        val result = assignationRule.genericLine(ast)
        assertEquals("a=5", result)
    }

    @Test
    fun test003enforceRule() {
        var assignationRule: Rules = AssignationRule("EqualFront", "EqualBack")
        val code = "a=5"
        assignationRule = assignationRule.isTheRuleIncluded(mapOf("EqualFront" to 1, "EqualBack" to 1))
        val result = assignationRule.enforceRule(code)
        assertEquals("a = 5;", result)
    }

    @Test
    fun test004enforceRule() {
        var assignationRule: Rules = AssignationRule("EqualFront", "EqualBack")
        val code = "a=5"
        assignationRule = assignationRule.isTheRuleIncluded(mapOf("EqualFront" to 1, "EqualBack" to 1))
        val result = assignationRule.enforceRule(code)
        assertEquals("a = 5;", result)
    }

    @Test
    fun test005assignationWithOperation() {
        val ast =
            astn.Assignation(
                Token(DataType.VARIABLE_NAME, "a", Pair(0, 0), Pair(1, 0)),
                astn.OperationHead(
                    Token(DataType.OPERATOR_PLUS, "+", Pair(4, 0), Pair(5, 0)),
                    OperationNumber(
                        Token(DataType.NUMBER_TYPE, "5", Pair(2, 0), Pair(3, 0)),
                    ),
                    OperationNumber(
                        Token(DataType.NUMBER_TYPE, "5", Pair(2, 0), Pair(3, 0)),
                    ),
                ),
            )
        val assignationRule: Rules = AssignationRule("EqualFront", "EqualBack")
        val result = assignationRule.genericLine(ast)
        assertEquals("a=5+5", result)
    }

    @Test
    fun test006assignationEqualsReadInput() {
        val ast =
            astn.Assignation(
                Token(DataType.VARIABLE_NAME, "a", Pair(0, 0), Pair(1, 0)),
                astn.OperationInput(
                    OperationNumber(
                        Token(DataType.NUMBER_TYPE, "5", Pair(2, 0), Pair(3, 0)),
                    ),
                ),
            )
        val assignationRule: Rules = AssignationRule("EqualFront", "EqualBack")
        val result = assignationRule.genericLine(ast)
        assertEquals("a=readInput(5)", result)
    }
}
