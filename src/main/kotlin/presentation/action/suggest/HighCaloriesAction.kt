package presentation.action.suggest

import domain.use_case.suggest.SuggestHighCalorieMealsUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class HighCaloriesAction(
    private val getMealsContainsHighCaloriesUseCase: SuggestHighCalorieMealsUseCase
) : MenuAction {
    override val description: String = "High-Calorie Meals for Thin People"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("=== Meal with High Calories for Thin People ===")

        while (true) {
            ui.displayResult("Here is a meal for you:")
            val meal = getMealsContainsHighCaloriesUseCase.getNextMeal()

            ui.displayResult("\nMeal: ${meal.name}")
            ui.displayResult("Description: ${meal.description}\n")
            ui.displayPrompt("Did you like this meal? (y/n): ")
            when (inputReader.readString().trim().lowercase()) {
                "y" -> {
                    ui.displayResult("Great! Enjoy your meal.")
                    ui.displayResult(meal.toString())
                    break
                }
                "n" -> ui.displayResult("Let's try another one.")
                else -> ui.displayError("Invalid input. Please enter 'y' or 'n'.")
            }
            ui.displayResult("------------------------------------------------------------")
        }
    }
}