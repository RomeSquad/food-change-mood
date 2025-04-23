package presentation.io

import presentation.MenuAction

class ConsoleUi : UiExecutor {
    override fun displayMenu(options: List<MenuAction>) {
        options.forEachIndexed{ index, option ->
            displayResult("${index + 1}- ${option.description}")
        }
        displayPrompt("Choose the action from (1)..(${options.size}) or anything else to exit: ")
    }

    override fun displayResult(message: String) = println(message)

    override fun displayError(message: String) = println("Error: $message")

    override fun displayPrompt(prompt: String) {
        val yellow = "\u001B[33m"
        val reset = "\u001B[0m"
        print("$yellow$prompt$reset")
    }
}