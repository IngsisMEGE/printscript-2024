package org.example

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import java.io.File

fun main(args: Array<String>) {
    val printScript = PrintScript()
    Cli(printScript).subcommands(
        Execute(printScript),
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

class Execute(private val printScript: PrintScript) : CliktCommand(help = "Execute a PrintScript file") {
    private val filePath: String by option(help = "Path to the PrintScript file").prompt("Enter the file path")
    private val outputsPath: String by option(help = "Path to the PrintScript outputs").prompt("Enter the outputs path")

    override fun run() {
        try {
            val output = printScript.start(filePath, outputsPath)
            echo(output)
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}

class FormatFile(private val printScript: PrintScript) : CliktCommand(help = "Format a PrintScript file") {
    private val filePath: String by option(help = "Path to the PrintScript file to format").prompt("Enter the file path")

    override fun run() {
        try {
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

    override fun run() {
        try {
            val analysis = printScript.analyze(filePath)
            echo(analysis)
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}

class ChangeFormatterConfig(private val printScript: PrintScript) : CliktCommand(help = "Change formatter configurations") {
    private val configFilePath: String by option(help = "Path to the configuration file").prompt("Enter the configuration file path")

    override fun run() {
        try {
            printScript.changeFormatterConfig(configFilePath)
            echo("Formatter configurations updated successfully.")
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }
}

class ChangeScaConfig(private val printScript: PrintScript) : CliktCommand(help = "Change SCA configurations") {
    private val configFilePath: String by option(
        help = "Path to the SCA configuration file",
    ).prompt("Enter the SCA configuration file path")

    override fun run() {
        try {
            printScript.changeSCAConfig(configFilePath)
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
