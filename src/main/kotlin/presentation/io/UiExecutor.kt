package presentation.io

import presentation.MenuAction

interface UiExecutor {
    fun displayMenu(options:List<MenuAction>)
    fun displayResult(message: String)
    fun displayError(message: String)
    fun displayPrompt(prompt: String)
}



