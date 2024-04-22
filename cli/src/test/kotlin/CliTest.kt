import com.github.ajalt.clikt.testing.test
import org.example.Execute
import org.example.FormatFile
import org.example.PrintScript
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class CliTest {
    private val printScript = PrintScript()

    @Test
    fun testExecuteCommand() {
        val command = Execute(printScript)
        val result = command.test("--file-path=src/test/resources/testFile.txt")
        assertEquals("10\n\n", result.output)
    }

    @Test
    fun testFinalFormattedFile() {
        val command = FormatFile(printScript)
        command.test("--file-path=src/test/resources/testFile.txt --version=1.1")
        val expectedContent = "let x:number=10;\n\nprintln(x);\n"
        val actualContent = File("src/test/resources/testFile.txt").readText()
        assertEquals(expectedContent, actualContent)
    }

    @Test
    fun test003ExecuteCommandWithOneDotZeroVersionShouldThrowError() {
        val command = Execute(printScript)
        val result = command.test("--file-path=src/test/resources/testOnePointZeroShouldError.txt --version=1.0")
        assertEquals("Error: File does not end with a separator\n", result.output)
    }

    @Test
    fun test004ExecuteCommandWithOneDotZeroVersionShouldThrowError() {
        val command = Execute(printScript)
        val result = command.test("--file-path=src/test/resources/testOnePointZeroShouldError2.txt --version=1.0")
        assertEquals("Error: Invalid operation at Line 1\n", result.output)
    }

    @Test
    fun test005ExecuteCommandWithOneDotZeroVersionShouldWork() {
        val command = Execute(printScript)
        val result = command.test("--file-path=src/test/resources/testOnePointZeroShouldWork.txt --version=1.0")
        assertEquals(
            "1\n" +
                "32\n" +
                "0.5\n" +
                "\n",
            result.output,
        )
    }
}
