package presentation.action.suggest

import domain.use_case.suggest.SuggestKetoMealUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class KetoDietAction(
    private val getKetoDietMealsUseCase: SuggestKetoMealUseCase
) : MenuAction {
    override val description: String = "Keto Diet Meal Suggestions"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        try {
            displayWelcomeMessage(ui)
            suggestKetoMeals(ui, inputReader)
        } finally {
            displaySeparator(ui)
        }
    }

    private fun displayWelcomeMessage(ui: UiExecutor) {
        ui.displayResult("Welcome to your Keto Diet Helper")
    }

    private fun suggestKetoMeals(ui: UiExecutor, inputReader: InputReader) {
        do {
            try {
                displayNextKetoMeal(ui)
            } catch (e: Exception) {
                handleKetoMealError(ui, e)
            }
        } while (shouldContinue(ui, inputReader))
    }

    private fun displayNextKetoMeal(ui: UiExecutor) {
        val meal = getKetoDietMealsUseCase.getNextKetoMeal()
        ui.displayResult("We suggest to you:\n$meal")
    }

    private fun handleKetoMealError(ui: UiExecutor, exception: Exception) {
        ui.displayError(exception.message ?: "Error fetching keto meal")
    }

    private fun shouldContinue(ui: UiExecutor, inputReader: InputReader): Boolean {
        ui.displayPrompt("Do you want to see another keto meal suggestion? (y/n): ")
        return when (inputReader.readString().lowercase().trim()) {
            "y" -> {
                ui.displayResult("Here is another one")
                true
            }

            "n" -> false
            else -> {
                ui.displayError("Please enter 'y' or 'n'")
                true
            }
        }
    }


}