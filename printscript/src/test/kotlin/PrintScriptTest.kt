import org.example.PrintScript
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.FileNotFoundException
import kotlin.test.assertEquals

class PrintScriptTest {
    @Test
    fun testCLIWithValidFilePath() {
        val printScript = PrintScript()
        val path = "src/test/resources/testFile.txt"
        val expectedOutput = "10\n" // Devolvemos empty porque asi es la definicion del interpreter para un varDeclaration
        Assertions.assertEquals(expectedOutput, printScript.start(path))
    }

    @Test
    fun testCLIWithInvalidFilePath() {
        val cli = PrintScript()
        val path = "src/test/resources/invalidFile.txt"
        assertThrows<FileNotFoundException> {
            cli.start(path)
        }
    }

    @Test
    fun testWithSameLine2VariablesWithSemicolon() {
        val printScript = PrintScript()
        val path = "src/test/resources/sameLineFile.txt"
        val expectedOutput = "4\n"
        assertEquals(expectedOutput, printScript.start(path))
    }

    @Test
    fun testFullIterationVarDeclaration() {
        val printScript = PrintScript()
        val path = "src/test/resources/testFile.txt"
        val expectedOutput = "10\n"
        assertEquals(expectedOutput, printScript.start(path))
    }

    @Test
    fun testOutputAssignation() {
        val printScript = PrintScript()
        val path = "src/test/resources/testFile.txt"
        val expectedOutput = "10\n"
        assertEquals(expectedOutput, printScript.start(path))
    }

    @Test
    fun testFormatFile() {
        val printScript = PrintScript()
        val path = "src/test/resources/fileToFormat.txt"
        val expectedOutput = "let x:number=10;\nlet y:number=20;\nx=y + 43 / 4;\nx=y + 43 / 4;\n"
        assertEquals(expectedOutput, printScript.format(path))
    }

    @Test
    fun test005LargeFileSuccess() {
        val printScript = PrintScript()
        val path = "src/test/resources/longFile.txt"
        val expected =
            "25\n" +
                "490\n" +
                "405\n" +
                "67\n" +
                "Hola Mundo\n" +
                "12250\n" +
                "0\n" +
                "12250\n" +
                "3113\n" +
                "0\n" +
                "0\n" +
                "199900\n" +
                "499\n" +
                "-997501\n" +
                "Prueba Lexer\n" +
                "Parser Interpreter\n" +
                "Prueba LexerParser Interpreter\n" +
                "-497553099\n" +
                "1988150668\n" +
                "PucaQuiereaGaru-4975530991988150668\n" +
                "-1492051991\n" +
                "-1492053744\n"
        assertEquals(expected, printScript.start(path))
    }

    @Test
    fun test006DeclarationWithSpaceAndAssignation() {
        val printScript = PrintScript()
        val path = "src/test/resources/StringWithSpaceAssignationAndDeclaration.txt"
        val expectedOutput =
            "let a:string=\"Hello World\";\n" +
                "a=\"GoodBye World\";\n"
        assertEquals(expectedOutput, printScript.format(path))
        assertEquals("", printScript.start(path))
    }

    @Test
    fun test007LineDiscontinued() {
        val printScript = PrintScript()
        val path = "src/test/resources/discontinuedLineTest.txt"
        val expectedOutput = "3\n"
        assertEquals(expectedOutput, printScript.start(path))
    }

    @Test
    fun test008ApostropheString() {
        val printScript = PrintScript()
        val path = "src/test/resources/apostropheStringTest.txt"
        val expectedOutput = "Hello World!\n"
        assertEquals(expectedOutput, printScript.start(path))
    }

    @Test
    fun test009BasicOperation() {
        val printScript = PrintScript()
        val path = "src/test/resources/operationFile.txt"
        val expectedOutput = "30\n"
        val realOutput = printScript.start(path)
        assertEquals(expectedOutput, realOutput)
    }

    @Test
    fun test010OperationsAndVariableReassignation() {
        val printScript = PrintScript()
        val path = "src/test/resources/operationAndVariableReassignation.txt"
        val expectedOutput = "15\n" + "10\n" + "20\n"
        val realOutput = printScript.start(path)
        assertEquals(expectedOutput, realOutput)
    }

    @Test
    fun test011StringNumberConcatenation() {
        val printScript = PrintScript()
        val path = "src/test/resources/stringNumberConcatenation.txt"
        val expectedOutput = "Hello world 10\n"
        val realOutput = printScript.start(path)
        assertEquals(expectedOutput, realOutput)
    }

    @Test
    fun test012AnalyzeWithSCAIssues() {
        val printScript = PrintScript()
        val path = "src/test/resources/testFileWithSCAIssues.txt"
        val expectedOutput = "Invalid typing format in line 4 row 1"
        assertEquals(expectedOutput, printScript.analyze(path))
    }

    @Test
    fun test013IfFileDoesNotEndWithSeparatorShouldThrowException() {
        val printScript = PrintScript()
        val path = "src/test/resources/testFileNotEndWithSeparator.txt"
        val expectedOutput = "An error occurred while executing the script. File does not end with a separator"
        assertEquals(expectedOutput, printScript.start(path))
    }
}
