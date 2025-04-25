package presentation

import presentation.io.InputReader
import presentation.io.UiExecutor

class App (
    private val uiExecutor: UiExecutor,
    private val inputReader: InputReader,
    private val menu: Menu
) {
    fun start() {
        do {
            processUserMenuSelection()
        } while (shouldContinue())
        uiExecutor.displayResult("Goodbye")
    }

    private fun processUserMenuSelection() {
        try {
            displayMenuAndExecuteAction()
        } catch (e: IllegalArgumentException) {
            uiExecutor.displayError(e.message ?: "Invalid input provided")
        } catch (e: Exception) {
            uiExecutor.displayError(e.message ?: "An unexpected error occurred")
        }
    }

    private fun displayMenuAndExecuteAction() {
        uiExecutor.displayMenu(menu.getActions())
        val selectedAction = menu.getAction(inputReader.readIntOrNull()) ?: return
        selectedAction.execute(uiExecutor, inputReader)
    }

    private fun shouldContinue(): Boolean {
        uiExecutor.displayPrompt("Do you want to perform another action? (y/n): ")
        return inputReader.readString().equals("y", ignoreCase = true)
    }
}