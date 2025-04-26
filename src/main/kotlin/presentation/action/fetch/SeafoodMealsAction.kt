package presentation.action.fetch

import data.model.seafood_meals.SeafoodMeal
import domain.use_case.fetch.GetSeafoodMealsUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class SeafoodMealsAction(
    private val getRankedSeafoodByProteinUseCase: GetSeafoodMealsUseCase
) : MenuAction {
    override val description: String = "Seafood Meals Sorted by Protein"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        try {
            displayRankedSeafoodMeals(ui)
        } finally {
            displaySeparator(ui)
        }
    }

    private fun displayRankedSeafoodMeals(ui: UiExecutor) {
        ui.displayHeader()
        val meals = fetchRankedSeafoodMeals()

        if (meals.isEmpty()) {
            ui.displayResult("No seafood meals available.")
        } else {
            meals.forEachIndexed { index, meal ->
                displayMealEntry(ui, index, meal)
            }
        }
    }

    private fun UiExecutor.displayHeader() {
        this.displayResult("--- Seafood Meals Sorted by Protein (Highest First) ---")
    }

    private fun fetchRankedSeafoodMeals(): List<SeafoodMeal> {
        return getRankedSeafoodByProteinUseCase.getSeafoodMeals()
    }

    private fun displayMealEntry(ui: UiExecutor, position: Int, meal: SeafoodMeal) {
        ui.displayResult("${position + 1}. ${meal.name} - Protein: ${meal.proteinPerGram}g")
    }
}