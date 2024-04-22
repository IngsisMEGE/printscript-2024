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
        val result = command.test("--file-path=src/test/resources/testFile.txt --version=1.0 --outputs-path=")
        assertEquals("10\n\n", result.output)
    }

    @Test
    fun testFinalFormattedFile() {
        val command = FormatFile(printScript)
        command.test("--file-path=src/test/resources/testFile.txt")
        val expectedContent = "let x:number=10;\n\nprintln(x);\n"
        val actualContent = File("src/test/resources/testFile.txt").readText()
        assertEquals(expectedContent, actualContent)
    }
}
