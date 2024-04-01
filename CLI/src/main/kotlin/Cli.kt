package org.example

import java.io.FileNotFoundException
import java.lang.Exception

/**
 * This class represents the Command Line Interface (CLI) for the PrintScript application.
 * It provides an interactive menu for the user to interact with the application.
 *
 * @property printScript An instance of the PrintScript class which is used to start the script execution.
 */
class Cli(val printScript: PrintScript = PrintScript())

/**
 * The main function which starts the CLI.
 * It provides a loop which continuously presents the user with a menu of options until the user chooses to exit.
 * The options include providing a file path for script execution, trying separate modules, changing formatter configurations, and exiting the application.
 */

fun main() {
    val cli = Cli()
    while (true) {
        printASCII()
        println("1. Provide a file path")
        println("2. Try separate modules")
        println("3. Change configurations for the formatter")
        println("4. Exit")

        val input = readlnOrNull()

        when (input) {
            "1" -> {
                println("Please enter the file path:")
                val filePath = readlnOrNull()
                try {
                    if (filePath != null) {
                        cli.printScript.start(filePath)
                    }
                } catch (e: FileNotFoundException) {
                    println("File not found: $filePath")
                } catch (e: Exception) {
                    println("An error occurred: ${e.message}")
                }
            }

            "2" -> {
                // .trySeparateModules()
            }

            "3" -> {
                // printScript.changeFormatterConfigurations()
            }

            "4" -> {
                println("Exiting...")
                return
            }

            else -> {
                println("Invalid option. Please try again.")
            }
        }
    }
}

private fun printASCII() {
    val topAndBottomBorder = "+-----------------------+"
    val emptyLine = "|                       |"
    val textLine = "| PrintScript Ing Sis   |"

    println(topAndBottomBorder)
    println(emptyLine)
    println(textLine)
    println(emptyLine)
    println(topAndBottomBorder)
    println("Welcome to the CLI! Please select an option:")
    println()
}
