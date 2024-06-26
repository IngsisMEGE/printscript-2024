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
        val expectedOutput =
            "10\n" // Devolvemos empty porque asi es la definicion del interpreter para un varDeclaration
        Assertions.assertEquals(expectedOutput, printScript.start(path, ""))
    }

    @Test
    fun testCLIWithInvalidFilePath() {
        val cli = PrintScript()
        val path = "src/test/resources/invalidFile.txt"
        assertThrows<FileNotFoundException> {
            cli.start(path, "")
        }
    }

    @Test
    fun testWithSameLine2VariablesWithSemicolon() {
        val printScript = PrintScript()
        val path = "src/test/resources/sameLineFile.txt"
        val expectedOutput = "4\n"
        assertEquals(expectedOutput, printScript.start(path, ""))
    }

    @Test
    fun testFullIterationVarDeclaration() {
        val printScript = PrintScript()
        val path = "src/test/resources/testFile.txt"
        val expectedOutput = "10\n"
        assertEquals(expectedOutput, printScript.start(path, ""))
    }

    @Test
    fun testOutputAssignation() {
        val printScript = PrintScript()
        val path = "src/test/resources/testFile.txt"
        val expectedOutput = "10\n"
        assertEquals(expectedOutput, printScript.start(path, ""))
    }

    @Test
    fun testFormatFile() {
        val printScript = PrintScript()
        val path = "src/test/resources/fileToFormat.txt"
        val expectedOutput = "let x : number = 10;\nlet y : number = 20;\nx = y + 43 / 4;\nx = y + 43 / 4;\n"
        assertEquals(expectedOutput, printScript.format(path))
    }

    @Test
    fun test005LargeFileSuccess() {
        val printScript = PrintScript()
        val path = "src/test/resources/longFile.txt"
        val expected =
            "24.2\n" +
                "474\n" +
                "416.7355371900827\n" +
                "779.5884297520668\n" +
                "Hola Mundo\n" +
                "11470.8\n" +
                "1.8707030242935065\n" +
                "11468.929296975706\n" +
                "266.316990741035\n" +
                "996.399999999787\n" +
                "996.399999999787\n" +
                "199900\n" +
                "499.75\n" +
                "-999000.25\n" +
                "Prueba Lexer\n" +
                "Parser Interpreter\n" +
                "Prueba LexerParser Interpreter\n" +
                "-4.990504749375E8\n" +
                "-9.976018994000625E13\n" +
                "PucaQuiereaGaru-4.990504749375E8-9.976018994000625E13\n" +
                "-1.994216172919719E19\n" +
                "-1.9983670291953082E19\n"
        assertEquals(expected, printScript.start(path, ""))
    }

    @Test
    fun test006DeclarationWithSpaceAndAssignation() {
        val printScript = PrintScript()
        val path = "src/test/resources/StringWithSpaceAssignationAndDeclaration.txt"
        val expectedOutput =
            "let a : string = \"Hello World\";\n" +
                "a = \"GoodBye World\";\n"
        assertEquals(expectedOutput, printScript.format(path))
        assertEquals("", printScript.start(path, ""))
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
    fun test014BooleanPlusStringShouldThrow() {
        val printScript = PrintScript()
        val path = "src/test/resources/booleanTest.txt"
        assertThrows<Exception> {
            printScript.start(path)
        }
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
        assertThrows<Exception> {
            printScript.start(path, "")
        }
    }

    @Test
    fun test014SimpleIfTest() {
        val printScript = PrintScript()
        val path = "src/test/resources/fileSimpleIfTest.txt"
        val expectedOutput = "1\n"
        assertEquals(expectedOutput, printScript.start(path, ""))
    }

    @Test
    fun test015SimpleIfElseTest() {
        val printScript = PrintScript()
        val path = "src/test/resources/fileSimpleIfWithElseTest.txt"
        val expectedOutput = "1\n"
        assertEquals(expectedOutput, printScript.start(path, ""))
    }

    @Test
    fun test016IfInsideIfTest() {
        val printScript = PrintScript()
        val path = "src/test/resources/fileIfInsideIfTest.txt"
        val expectedOutput = "Hola\n"
        assertEquals(expectedOutput, printScript.start(path, ""))
    }

    @Test
    fun test017fileIfVariableCreatedInsideIfShouldNotBeRecognized() {
        val printScript = PrintScript()
        val path = "src/test/resources/fileIfVariableCreatedInsideIfShouldNotBeRecognized.txt"
        assertThrows<Exception> {
            printScript.start(path, "")
        }
    }

    @Test
    fun test018fileIfVariableFormattedCorrectly() {
        val printScript = PrintScript()
        val path = "src/test/resources/fileIfVariableFormattedCorrectly.txt"
        val expectedOutput = "let a : number = 10;\nif(a) {\n    let b : number = 20;\n}\n"
        assertEquals(expectedOutput, printScript.format(path))
    }

    @Test
    fun test019fileIfVariableNestedIfFormattedCorrectly() {
        val printScript = PrintScript()
        val path = "src/test/resources/fileIfVariableNestedIfFormattedCorrectly.txt"
        val expectedOutput =
            "let a : number = 10;\nif(a) {\n    let b : number = 20;\n    if(b) {\n        let c : number = 30;\n    }\n}\n"
        assertEquals(expectedOutput, printScript.format(path))
    }

    @Test
    fun test020UpdateLexerConfigurationAndParseFile() {
        val printScript = PrintScript()
        val path = "src/test/resources/clitestDeclar.txt"
        val expectedOutput = "25\n"
        printScript.updateRegexRules("src/test/resources/newRegex.json")
        assertEquals(expectedOutput, printScript.start(path, ""))
        printScript.updateRegexRules("src/test/resources/LexerFullRules.json")
    }

    @Test
    fun test021ReadInput() {
        val printScript = PrintScript()
        val path = "src/test/resources/readinput.txt"
        val output = "src/test/resources/outputs.txt"
        val expectedOutput =
            "Name: \nhello world\n"
        Assertions.assertEquals(expectedOutput, printScript.start(path, output))
        assertThrows<Exception> {
            printScript.start(path, "")
        }
    }

    @Test
    fun test014ErrorShouldThrowException() {
        val printScript = PrintScript()
        val path = "src/test/resources/testFileWithError.txt"
        assertThrows<Exception> {
            printScript.start(path, "")
        }
    }

    @Test
    fun test022PrintTestWorkingCorrectly() {
        val printScript = PrintScript()
        val path = "src/test/resources/finalTest.txt"
        val expectedOutput = "else statement working correctly\noutside of conditional\n"
        assertEquals(expectedOutput, printScript.start(path, ""))
    }

    @Test
    fun test023ReadEnvShouldPrintCorrectly() {
        val printScript = PrintScript()
        System.setProperty("Hello", "World")
        val path = "src/test/resources/readEnvTest.txt"
        val expectedOutput = "5World\n"
        assertEquals(expectedOutput, printScript.start(path, ""))
        System.clearProperty("Hello")
    }
}
