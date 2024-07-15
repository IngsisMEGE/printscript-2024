package org.example

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import java.io.File

fun main(args: Array<String>) {
    val inputLoader = InputLoader()
    val printScript = PrintScript(inputLoader::loadInput)
    Cli(printScript).subcommands(
        Execute(printScript, inputLoader),
        FormatFile(printScript),
        Analyze(printScript),
        ChangeFormatterConfig(printScript),
        ChangeLexerConfig(printScript),
        ChangeScaConfig(printScript),
    ).main(args)
}

class Cli(private val printScript: PrintScript) : CliktCommand() {
    override fun run() = echo("Welcome to PrintScript CLI. Use --help to see the options.")
}

class Execute(private val printScript: PrintScript, private val inputLoader: InputLoader) : CliktCommand(help = "Execute a PrintScript file") {
    private val filePath: String by option(help = "Path to the PrintScript file").prompt("Enter the file path")
    private val inputPath: String by option(help = "Path to the PrintScript outputs").default("src/main/resources/output.txt")
    private val version: String by option(help = "PrintScript version").default("use-default")

    override fun run() {
        try {
            inputLoader.addLinesToQueue(inputPath)
            if (version == "1.0") {
                printScript.updateRegexRules("src/main/resources/LexerRegex0v.json")
            } else if (version == "1.1") {
                printScript.updateRegexRules("src/main/resources/LexerFullRules.json")
            }

            val output = printScript.start(filePath)
            echo(output)
            inputLoader.cleanOutputs()
        } catch (e: Exception) {
            inputLoader.cleanOutputs()
            echo("Error: ${e.message}", err = true)
        }
    }
}

class FormatFile(private val printScript: PrintScript) : CliktCommand(help = "Format a PrintScript file") {
    private val filePath: String by option(help = "Path to the PrintScript file to format").prompt("Enter the file path")
    private val version: String by option(help = "PrintScript version").default("use-default")

    override fun run() {
        try {
            if (version == "1.0") {
                printScript.updateRegexRules("src/main/resources/LexerRegex0v.json")
            } else if (version == "1.1") {
                printScript.updateRegexRules("src/main/resources/LexerFullRules.json")
            }

            val formattedContent = printScript.format(filePath)
            File(filePath).writeText(formattedContent)
            echo("File formatted and updated successfully.")
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}

class Analyze(private val printScript: PrintScript) : CliktCommand(help = "Analyze a PrintScript file") {
    private val filePath: String by option(help = "Path to the PrintScript file to analyze").prompt("Enter the file path")
    private val version: String by option(help = "PrintScript version").default("user-defined")

    override fun run() {
        try {
            if (version == "1.0") {
                printScript.updateRegexRules("src/main/resources/LexerRegex0v.json")
            } else if (version == "1.1") {
                printScript.updateRegexRules("src/main/resources/LexerFullRules.json")
            }

            val analysis = printScript.analyze(filePath)
            echo(analysis)
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}

class ChangeFormatterConfig(private val printScript: PrintScript) : CliktCommand(help = "Change formatter configurations") {
    private val filePath: String by option(help = "Path to the configuration file").prompt("Enter the configuration file path")

    override fun run() {
        try {
            printScript.changeFormatterConfig(filePath)
            echo("Formatter configurations updated successfully.")
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}

class ChangeScaConfig(private val printScript: PrintScript) : CliktCommand(help = "Change SCA configurations") {
    private val filePath: String by option(
        help = "Path to the SCA configuration file",
    ).prompt("Enter the SCA configuration file path")

    override fun run() {
        try {
            printScript.changeSCAConfig(filePath)
            echo("SCA configurations updated successfully.")
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}

class ChangeLexerConfig(private val printScript: PrintScript) : CliktCommand(help = "Change lexer configurations") {
    private val filePath: String by option(
        help = "Path to the lexer configuration file",
    ).prompt("Enter the lexer configuration file path")

    override fun run() {
        try {
            printScript.updateRegexRules(filePath)
            echo("Lexer configurations updated successfully.")
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}

class InputLoader() {
    private val outputs = mutableListOf<String>()

    fun addLinesToQueue(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            file.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    outputs.add(line)
                }
            }
            File(filePath).delete()
        } else {
            return
        }
    }

    fun loadInput(): String {
        return if (outputs.isEmpty()) {
            ""
        } else {
            outputs.removeAt(0)
        }
    }

    fun cleanOutputs() {
        outputs.clear()
    }
}
