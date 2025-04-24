package presentation.action.search

import data.model.Meal
import data.model.gym_helper.CaloriesAndProteinTolerance
import data.model.gym_helper.GymHelperInput
import domain.use_case.search.SearchGymHelperMealsUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class MealsByCaloriesAndProteinAction(
    private val gymHelperUseCase: SearchGymHelperMealsUseCase
) : MenuAction {
    override val description: String = "Meals Based on Calories and Proteins"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        try {
            ui.displayResult("--- Find Meals by Calories & Protein ---")

            val searchCriteria = promptForSearchCriteria(ui, inputReader) ?: return
            val meals = searchMeals(searchCriteria)

            displayResults(ui, meals)
        } finally {
            displaySeparator(ui)
        }
    }

    private fun promptForSearchCriteria(ui: UiExecutor, inputReader: InputReader): GymHelperInput? {
        val calories = promptForDouble(ui, inputReader, "Enter desired calories (e.g., 500): ")
        val protein = promptForDouble(ui, inputReader, "Enter desired protein (e.g., 30): ")
        val caloriesTolerance = promptForIntWithDefault(
            ui, inputReader,
            "Enter calories tolerance (default is 30): ", 30
        )
        val proteinTolerance = promptForIntWithDefault(
            ui, inputReader,
            "Enter protein tolerance (default is 10): ", 10
        )

        return if (isValidInput(calories, protein, caloriesTolerance, proteinTolerance)) {
            GymHelperInput(
                calories = requireNotNull(calories),
                protein = requireNotNull(protein),
                caloriesAndProteinTolerance = CaloriesAndProteinTolerance(
                    caloriesTolerance,
                    proteinTolerance
                )
            )
        } else {
            ui.displayError("Invalid input. Please enter positive numeric values.")
            null
        }
    }

    private fun promptForDouble(ui: UiExecutor, inputReader: InputReader, prompt: String): Double? {
        ui.displayPrompt(prompt)
        return inputReader.readDoubleOrNull()
    }

    private fun promptForIntWithDefault(ui: UiExecutor, inputReader: InputReader, prompt: String, default: Int): Int {
        ui.displayPrompt(prompt)
        return inputReader.readIntOrNull() ?: default
    }

    private fun isValidInput(
        calories: Double?,
        protein: Double?,
        caloriesTolerance: Int,
        proteinTolerance: Int
    ): Boolean {
        return calories != null &&
                protein != null &&
                caloriesTolerance >= 0 &&
                proteinTolerance >= 0
    }

    private fun searchMeals(criteria: GymHelperInput): List<Meal> {
        return gymHelperUseCase.getMealsByCaloriesAndProtein(criteria)
    }

    private fun displayResults(ui: UiExecutor, meals: List<Meal>) {
        if (meals.isEmpty()) {
            ui.displayResult("No meals found matching the criteria.")
            return
        }

        ui.displayResult("Meals matching the criteria:")
        meals.forEachIndexed { index, meal ->
            displayMealDetails(ui, meal, index + 1)
        }
    }

    private fun displayMealDetails(ui: UiExecutor, meal: Meal, position: Int) {
        with(meal) {
            ui.displayResult("------------------------------------------------------------")
            ui.displayResult("Meal $position:")
            ui.displayResult("\n- $name")
            ui.displayResult("Description: $description")
            ui.displayResult("Calories: ${nutrition.calories}")
            ui.displayResult("Protein: ${nutrition.protein}")
            ui.displayResult("Preparation Time: $minutes minutes")
        }
    }
}