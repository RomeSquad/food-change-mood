package presentation.action.suggest

import domain.use_case.suggest.SuggestKetoMealUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class KetoDietAction(
    private val getKetoDietMealsUseCase: SuggestKetoMealUseCase
) : MenuAction {
    override val description: String = "Keto Diet Meal Suggestions"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("Welcome to your Keto Diet Helper")
        val message = "We suggest to you:\n"

        while (true) {
            try {
                ui.displayResult(message + getKetoDietMealsUseCase.getNextKetoMeal().toString())
            } catch (e: Exception) {
                ui.displayError(e.message ?: "Error fetching keto meal")
            }
            ui.displayPrompt("Do you want to see another keto meal suggestion? (y/n): ")
            when (inputReader.readString().lowercase().trim()) {
                "y" -> ui.displayResult("Here is another one")
                "n" -> break
                else -> ui.displayError("Please enter 'y' or 'n'")
            }
        }
        ui.displayResult("------------------------------------------------------------")
    }
}