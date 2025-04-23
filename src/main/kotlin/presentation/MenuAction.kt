package presentation

import domain.use_case.GetByNameUseCase
import domain.use_case.GetHealthyMealsFilterUseCase
import presentation.input_output.InputReader
import presentation.input_output.UiExecutor

interface MenuAction {
    val description: String
    fun execute(ui: UiExecutor, inputReader: InputReader)
}

class HealthyFastFoodAction(
    private val getHealthyMealsFilterUseCase: GetHealthyMealsFilterUseCase
) : MenuAction {
    override val description: String = "Healthy Fast Meals (15 minutes)"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        val healthyMeals = getHealthyMealsFilterUseCase.getHealthyFastMeals()
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
class MealByNameAction(
    private val getByNameUseCase: GetByNameUseCase
) : MenuAction {
    override val description: String = "Search Meal by Name"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayPrompt("Enter the name of the meal: ")
        val query = inputReader.readString()
        getByNameUseCase.getByName(query).onSuccess { meals ->
            meals.forEach { meal ->
                ui.displayResult("\nMeal found:")
                ui.displayResult(meal.toString())
            }
        }.onFailure { error ->
            ui.displayError(error.message ?: "Failed to find meal")
        }
        ui.displayResult("------------------------------------------------------------")
    }
}
///TODO COMPLETE