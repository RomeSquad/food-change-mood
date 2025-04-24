package presentation.action.suggest

import data.model.Meal
import domain.use_case.suggest.SuggestHighCalorieMealsUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class HighCaloriesAction(
    private val getMealsContainsHighCaloriesUseCase: SuggestHighCalorieMealsUseCase
) : MenuAction {
    override val description: String = "High-Calorie Meals for Thin People"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        try {
            displayHighCalorieMealSuggestion(ui, inputReader)
        } finally {
            displaySeparator(ui)
        }
    }

    private fun displayHighCalorieMealSuggestion(ui: UiExecutor, inputReader: InputReader) {
        ui.displayHeader(ui)

        while (true) {
            val meal = fetchNextHighCalorieMeal()
            displayMealDetails(ui, meal)

            when (getUserPreference(ui, inputReader)) {
                UserPreference.LIKED -> {
                    confirmMealSelection(ui, meal)
                    break
                }

                UserPreference.DISLIKED -> {
                    promptForNextMeal(ui)
                }

                UserPreference.INVALID -> {
                    showInvalidInputError(ui)
                }
            }
            ui.displayResult("------------------------------------------------------------")
        }
    }

    private fun UiExecutor.displayHeader(ui: UiExecutor) {
        ui.displayResult("=== Meal with High Calories for Thin People ===")
    }

    private fun fetchNextHighCalorieMeal(): Meal {
        return getMealsContainsHighCaloriesUseCase.getNextMeal()
    }

    private fun displayMealDetails(ui: UiExecutor, meal: Meal) {
        ui.displayResult("\nMeal: ${meal.name}")
        ui.displayResult("Description: ${meal.description}\n")
    }

    private fun getUserPreference(ui: UiExecutor, inputReader: InputReader): UserPreference {
        ui.displayPrompt("Did you like this meal? (y/n): ")
        return when (inputReader.readString().trim().lowercase()) {
            "y" -> UserPreference.LIKED
            "n" -> UserPreference.DISLIKED
            else -> UserPreference.INVALID
        }
    }

    private fun confirmMealSelection(ui: UiExecutor, meal: Meal) {
        ui.displayResult("Great! Enjoy your meal.")
        ui.displayResult(meal.toString())
    }

    private fun promptForNextMeal(ui: UiExecutor) {
        ui.displayResult("Let's try another one.")
    }

    private fun showInvalidInputError(ui: UiExecutor) {
        ui.displayError("Invalid input. Please enter 'y' or 'n'.")
    }

    private enum class UserPreference {
        LIKED, DISLIKED, INVALID
    }
}