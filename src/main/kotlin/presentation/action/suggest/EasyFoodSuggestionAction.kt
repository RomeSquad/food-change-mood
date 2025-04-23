package presentation.action.suggest

import data.model.Meal
import domain.use_case.suggest.SuggestEasyFoodUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class EasyFoodSuggestionAction(
    private val getRandomMealsUseCase: SuggestEasyFoodUseCase
) : MenuAction {
    override val description: String = "Easy Food Suggestion Game"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        try {
            playFoodSuggestionGame(ui, inputReader)
        } finally {
            displaySeparator(ui)
        }
    }

    private fun playFoodSuggestionGame(ui: UiExecutor, inputReader: InputReader) {
        displayGameHeader(ui)
        displayDefaultMeals(ui)

        val mealCount = promptForMealCount(ui, inputReader)
        mealCount?.let { count ->
            displayCustomMeals(ui, count)
        }
    }

    private fun displayGameHeader(ui: UiExecutor) {
        ui.displayResult("\nEasy Food Suggestion Game, TEN RANDOM MEALS BY DEFAULT:")
    }

    private fun displayDefaultMeals(ui: UiExecutor) {
        getRandomMealsUseCase.getEasyFoodSuggestion().forEach { meal ->
            displayMeal(ui, meal)
        }
    }

    private fun promptForMealCount(ui: UiExecutor, inputReader: InputReader): Int? {
        ui.displayPrompt("\nEnter number of meals you want: ")
        return inputReader.readIntOrNull()
    }

    private fun displayCustomMeals(ui: UiExecutor, count: Int) {
        getRandomMealsUseCase.getEasyFoodSuggestion(count).forEach { meal ->
            displayMeal(ui, meal)
        }
    }

    private fun displayMeal(ui: UiExecutor, meal: Meal) {
        ui.displayResult("\nMeal Name: ${meal.name}\n")
    }
}