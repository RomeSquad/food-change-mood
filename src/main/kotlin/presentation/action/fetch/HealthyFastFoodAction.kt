package presentation.action.fetch

import data.model.Meal
import domain.use_case.fetch.GetQuickHealthyMealsUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class HealthyFastFoodAction(
    private val getQuickHealthyMealsUseCase: GetQuickHealthyMealsUseCase
) : MenuAction {
    override val description: String = "Healthy Fast Meals (15 minutes)"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        try {
            displayHealthyMeals(ui)
        } finally {
            displaySeparator(ui)
        }
    }

    private fun displayHealthyMeals(ui: UiExecutor) {
        val healthyMeals = fetchHealthyMeals()
        ui.displayResult("=== Healthy Fast Meals (≤15 minutes) ===")

        if (healthyMeals.isEmpty()) {
            ui.displayResult("No healthy fast meals available.")
        } else {
            healthyMeals.forEachIndexed { index, meal ->
                displayMealSummary(ui, index, meal)
            }
        }
    }

    private fun fetchHealthyMeals(): List<Meal> {
        return getQuickHealthyMealsUseCase.getQuickHealthyMeals()
    }

    private fun displayMealSummary(ui: UiExecutor, index: Int, meal: Meal) {
        ui.displayResult("${index + 1}. ${meal.name} - ${meal.minutes} min")
    }
}

