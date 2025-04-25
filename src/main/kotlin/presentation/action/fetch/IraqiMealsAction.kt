package presentation.action.fetch

import data.model.Meal
import domain.use_case.fetch.GetIraqiMealsUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class IraqiMealsAction(
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase
) : MenuAction {
    override val description: String = "Iraqi Meals"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        try {
            displayIraqiMeals(ui)
        } finally {
            displaySeparator(ui)
        }
    }

    private fun displayIraqiMeals(ui: UiExecutor) {
        val iraqiMeals = fetchIraqiMeals()
        if (iraqiMeals.isEmpty()) {
            ui.displayResult("No Iraqi meals found.")
            return
        }

        iraqiMeals.forEach { meal ->
            displayFormattedMeal(ui, meal)
        }
    }

    private fun fetchIraqiMeals(): List<Meal> {
        return getIraqiMealsUseCase.getIraqiMeals()
    }

    private fun displayFormattedMeal(ui: UiExecutor, meal: Meal) {
        ui.displayResult(meal.toString())
    }
}