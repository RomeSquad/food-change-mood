package presentation.action.search

import domain.use_case.search.SearchGymHelperMealsUseCase
import data.model.gym_helper.CaloriesAndProteinTolerance
import data.model.gym_helper.GymHelperInput
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class MealsByCaloriesAndProteinAction(
    private val gymHelperUseCase: SearchGymHelperMealsUseCase
) : MenuAction {
    override val description: String = "Meals Based on Calories and Proteins"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("--- Find Meals by Calories & Protein ---")

        ui.displayPrompt("Enter desired calories (e.g., 500): ")
        val calories = inputReader.readDoubleOrNull()

        ui.displayPrompt("Enter desired protein (e.g., 30): ")
        val protein = inputReader.readDoubleOrNull()

        ui.displayPrompt("Enter calories tolerance (default is 30): ")
        val caloriesTolerance = inputReader.readIntOrNull() ?: 30

        ui.displayPrompt("Enter protein tolerance (default is 10): ")
        val proteinTolerance = inputReader.readIntOrNull() ?: 10

        if (
            calories == null
            || protein == null
            || caloriesTolerance < 0
            || proteinTolerance < 0
        ) {
            ui.displayResult("Invalid input. Please enter numeric values.")
            return
        }

        val meals = gymHelperUseCase.getMealsByCaloriesAndProtein(
            input = GymHelperInput(
                calories = calories,
                protein = protein,
                caloriesAndProteinTolerance = CaloriesAndProteinTolerance(
                    caloriesTolerance, proteinTolerance
                )
            )
        )

        if (meals.isEmpty()) {
            ui.displayResult("No meals found matching the criteria.")
        } else {
            ui.displayResult("Meals matching the criteria:")
            meals.forEachIndexed { index, meal ->
                ui.displayResult("------------------------------------------------------------")
                ui.displayResult("Meal ${index + 1}:")
                ui.displayResult("\n- ${meal.name}")
                ui.displayResult("Description: ${meal.description}")
                ui.displayResult("Calories: ${meal.nutrition.calories}")
                ui.displayResult("Protein: ${meal.nutrition.protein}")
                ui.displayResult("Preparation Time: ${meal.minutes} minutes")
            }
        }
        ui.displayResult("------------------------------------------------------------")
    }
}