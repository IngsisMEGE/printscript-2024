import com.github.ajalt.clikt.testing.test
import org.example.Execute
import org.example.FormatFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class CliTest {
    @Test
    fun testExecuteCommand() {
        val command = Execute()
        val result = command.test("--file-path=src/test/resources/testFile.txt --outputs-path=")
        assertEquals("10\n\n", result.output)
    }

    @Test
    fun testFinalFormattedFile() {
        val command = FormatFile()
        command.test("--file-path=src/test/resources/testFile.txt")
        val expectedContent = "let x:number=10;\n\nprintln(x);\n"
        val actualContent = File("src/test/resources/testFile.txt").readText()
        assertEquals(expectedContent, actualContent)
    }
}
