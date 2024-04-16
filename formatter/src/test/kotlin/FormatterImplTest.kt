import astn.OperationNumber
import astn.OperationString
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import formatter.FormatterImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import rules.AssignationRule
import rules.MethodRule
import rules.Rules
import rules.VarDeclarationAssignationRule
import rules.VarDeclarationRule
import token.DataType
import token.Token

class FormatterImplTest {
    @Test
    fun test001formatMethodRule() {
        val ast =
            astn.Method(
                Token(DataType.VARIABLE_NAME, "println", Pair(0, 0), Pair(6, 0)),
                OperationString(
                    Token(
                        DataType.STRING_VALUE,
                        "Hello",
                        Pair(7, 0),
                        Pair(12, 0),
                    ),
                ),
            )
        val property = mapOf("JumpLine" to 1)
        val formatter = FormatterImpl(property)
        val expected = "\nprintln(\"Hello\");\n"
        val result = formatter.format(ast)
        assertEquals(expected, result)
    }

    @Test
    fun test003formatValDeclarationAssignation() {
        val ast: VarDeclarationAssignation =
            VarDeclarationAssignation(
                VarDeclaration(
                    Token(DataType.NUMBER_TYPE, "number", Pair(0, 0), Pair(4, 0)),
                    Token(DataType.VARIABLE_NAME, "dong", Pair(5, 0), Pair(8, 0)),
                ),
                OperationString(Token(DataType.STRING_VALUE, "Hola", Pair(12, 0), Pair(15, 0))),
            )
        val map: MutableMap<String, Any> = HashMap()
        map["DotFront"] = 1
        map["DotBack"] = 1
        map["EqualFront"] = 1
        map["EqualBack"] = 1
        val formatter = FormatterImpl(map)
        val expected = "let dong : number = \"Hola\";\n"
        val result = formatter.format(ast)
        assertEquals(expected, result)
    }

    @Test
    fun test004formatVarDeclaration() {
        val ast =
            astn.VarDeclaration(
                Token(DataType.NUMBER_TYPE, "number", Pair(4, 0), Pair(5, 0)),
                Token(DataType.VARIABLE_NAME, "x", Pair(0, 0), Pair(1, 0)),
            )
        val property: MutableMap<String, Any> = HashMap()
        property["SpaceInFront"] = 1
        property["SpaceInBack"] = 1
        val formatter = FormatterImpl(property)
        val expected = "let x : number;\n"
        val result = formatter.format(ast)
        assertEquals(expected, result)
    }

    @Test
    fun test005formatAssignation() {
        val ast =
            astn.Assignation(
                Token(DataType.VARIABLE_NAME, "a", Pair(0, 0), Pair(1, 0)),
                OperationNumber(
                    Token(DataType.NUMBER_TYPE, "5", Pair(2, 0), Pair(3, 0)),
                ),
            )
        val property: MutableMap<String, Any> = HashMap()
        property["EqualFront"] = 1
        property["EqualBack"] = 1
        val formatter = FormatterImpl(property)
        val expected = "a = 5;\n"
        val result = formatter.format(ast)
        assertEquals(expected, result)
    }
}
