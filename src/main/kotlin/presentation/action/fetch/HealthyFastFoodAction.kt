package presentation.action.fetch

import domain.use_case.fetch.GetQuickHealthyMealsUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class HealthyFastFoodAction(
    private val getQuickHealthyMealsUseCase: GetQuickHealthyMealsUseCase
) : MenuAction {
    override val description: String = "Healthy Fast Meals (15 minutes)"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        val healthyMeals = getQuickHealthyMealsUseCase.getQuickHealthyMeals()
        ui.displayResult("=== Healthy Fast Meals take 15 minutes ===")
        if (healthyMeals.isEmpty()) {
            ui.displayResult("No healthy fast meals found.")
        } else {
            healthyMeals.forEachIndexed { index, meal ->
                ui.displayResult("${index + 1}. ${meal.name} - ${meal.minutes} min")
            }
        }
        ui.displayResult("------------------------------------------------------------")
    }
}

