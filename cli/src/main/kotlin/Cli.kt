package org.example

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import java.io.File

fun main(args: Array<String>) =
    Cli().subcommands(
        Execute(),
        FormatFile(),
        Analyze(),
        ChangeFormatterConfig(),
        ChangeLexerConfig(),
    ).main(args)

class Cli : CliktCommand() {
    override fun run() = echo("Welcome to PrintScript CLI. Use --help to see the options.")
}

class Execute : CliktCommand(help = "Execute a PrintScript file") {
    private val filePath: String by option(help = "Path to the PrintScript file").prompt("Enter the file path")

    override fun run() {
        val printScript = PrintScript(::input)

        try {
            val output = printScript.start(filePath)
            echo(output)
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }

    private fun input(message: String): String {
        print(message)
        return readlnOrNull() ?: ""
    }
}

class FormatFile : CliktCommand(help = "Format a PrintScript file") {
    private val filePath: String by option(help = "Path to the PrintScript file to format").prompt("Enter the file path")

    override fun run() {
        try {
            val printScript = PrintScript(::input)
            val formattedContent = printScript.format(filePath)
            File(filePath).writeText(formattedContent)
            echo("File formatted and updated successfully.")
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }

    private fun input(message: String): String {
        print(message)
        return readlnOrNull() ?: ""
    }
}

class Analyze : CliktCommand(help = "Analyze a PrintScript file") {
    private val filePath: String by option(help = "Path to the PrintScript file to analyze").prompt("Enter the file path")

    override fun run() {
        try {
            val printScript = PrintScript(::input)
            val analysis = printScript.analyze(filePath)
            echo(analysis)
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }

    private fun input(message: String): String {
        print(message)
        return readlnOrNull() ?: ""
    }
}

class ChangeFormatterConfig : CliktCommand(help = "Change formatter configurations") {
    private val configFilePath: String by option(help = "Path to the configuration file").prompt("Enter the configuration file path")

    override fun run() {
        try {
            val printScript = PrintScript(::input)
            printScript.changeFormatterConfig(configFilePath)
            echo("Formatter configurations updated successfully.")
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }

    private fun input(message: String): String {
        print(message)
        return readlnOrNull() ?: ""
    }
}

class ChangeLexerConfig : CliktCommand(help = "Change lexer configurations") {
    private val configFilePath: String by option(
        help = "Path to the lexer configuration file",
    ).prompt("Enter the lexer configuration file path")

    override fun run() {
        try {
            val printScript = PrintScript(::input)
            printScript.updateRegexRules(configFilePath)
            echo("Lexer configurations updated successfully.")
        } catch (e: Exception) {
            echo("Error: ${e.message}", err = true)
        }
    }

    private fun input(message: String): String {
        print(message)
        return readlnOrNull() ?: ""
    }
}
