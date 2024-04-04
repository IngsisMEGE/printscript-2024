import org.example.PrintScript
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.FileNotFoundException

class PrintScriptTest {
    @Test
    fun testCLIWithValidFilePath() {
        val printScript = PrintScript()
        val path = "src/test/resources/testFile.txt"
        val expectedOutput = "" // Devolvemos empty porque asi es la definicion del interpreter para un varDeclaration
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
        val expectedOutput = "4"
        Assertions.assertEquals(expectedOutput, printScript.start(path))
    }

    // Modules testing

    @Test
    fun testFullIterationVarDeclaration() {
        val printScript = PrintScript()
        val path = "src/test/resources/testFile.txt"
        val expectedOutput = "" // Devolvemos empty porque asi es la definicion del interpreter para un varDeclaration
        Assertions.assertEquals(expectedOutput, printScript.start(path))
    }

    @Test
    fun testOutputAssignation() {
        val printScript = PrintScript()
        val path = "src/test/resources/testFile.txt"
        val expectedOutput = "10"
        Assertions.assertEquals(expectedOutput, printScript.start(path))
    }

    @Test
    fun testFormatFile() {
        val printScript = PrintScript()
        val path = "src/test/resources/fileToFormat.txt"
        val expectedOutput = "let x:number=10;\nlet y:number=20;\n"
        Assertions.assertEquals(expectedOutput, printScript.format(path))
    }
}
