package org.example

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import java.io.File

fun main(args: Array<String>) =
    Cli().subcommands(
        ExecuteScript(),
        FormatFile(),
        ChangeFormatterConfig(),
        ChangeLexerConfig(),
    ).main(args)

class Cli : CliktCommand() {
    override fun run() = echo("Welcome to PrintScript CLI. Use --help to see the options.")
}

class ExecuteScript : CliktCommand(help = "Execute a PrintScript file") {
    private val filePath: String by option(help = "Path to the PrintScript file").prompt("Enter the file path")
    private val version: String by option(help = "PrintScript version (1.0 or 1.1)").prompt("Enter PrintScript version (1.0 or 1.1)")

    override fun run() {
        val printScript =
            when (version) {
                "1.0" -> PrintScript()
                "1.1" -> PrintScript()
                else -> {
                    echo("Unsupported version. Defaulting to 1.0")
                    PrintScript()
                }
            }

        try {
            val output = printScript.start(filePath)
            echo(output)
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}

class FormatFile : CliktCommand(help = "Format a PrintScript file") {
    private val filePath: String by option(help = "Path to the PrintScript file to format").prompt("Enter the file path")

    override fun run() {
        try {
            val printScript = PrintScript()
            val formattedContent = printScript.format(filePath)
            File(filePath).writeText(formattedContent)
            echo("File formatted and updated successfully.")
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}

class ChangeFormatterConfig : CliktCommand(help = "Change formatter configurations") {
    private val configFilePath: String by option(help = "Path to the configuration file").prompt("Enter the configuration file path")

    override fun run() {
        try {
            val printScript = PrintScript()
            printScript.changeFormatterConfig(configFilePath)
            echo("Formatter configurations updated successfully.")
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}

class ChangeLexerConfig : CliktCommand(help = "Change lexer configurations") {
    private val configFilePath: String by option(
        help = "Path to the lexer configuration file",
    ).prompt("Enter the lexer configuration file path")

    override fun run() {
        try {
            val printScript = PrintScript()
            printScript.updateRegexRules(configFilePath)
            echo("Lexer configurations updated successfully.")
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}
