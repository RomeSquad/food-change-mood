package presentation.action.search

import domain.use_case.search.SearchMealsByNameUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class MealByNameAction(
    private val getByNameUseCase: SearchMealsByNameUseCase
) : MenuAction {
    override val description: String = "Search Meal by Name"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayPrompt("Enter the name of the meal: ")
        val query = inputReader.readString()
        getByNameUseCase.searchMealsByName(query).onSuccess { meals ->
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