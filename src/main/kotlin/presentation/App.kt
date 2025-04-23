package presentation

import domain.use_case.*
import logic.use_case.GetKetoDietMealsUseCase
import model.gym_helper.CaloriesAndProteinTolerance
import model.gym_helper.GymHelperInput
import presentation.input_output.InputReader
import presentation.input_output.UiExecutor

class App(
    private val uiExecutor: UiExecutor,
    private val inputReader: InputReader,
    private val menu: Menu
) {
    fun start() {
        while (true) {
            uiExecutor.displayMenu(menu.getActions())
            val selectedAction = menu.getAction(inputReader.readIntOrNull()) ?: break

            try {
                selectedAction.execute(uiExecutor, inputReader)
            } catch (e: IllegalArgumentException) {
                uiExecutor.displayError(e.message ?: "Invalid input provided")
            } catch (e: Exception) {
                uiExecutor.displayError(e.message ?: "An unexpected error occurred")
            }

            uiExecutor.displayPrompt("Do you want to perform another action? (y/n): ")
            if (inputReader.readString().lowercase() != "y") {
                uiExecutor.displayResult("Goodbye")
                break
            }
        }
    }
}