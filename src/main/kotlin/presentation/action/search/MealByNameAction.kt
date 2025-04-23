package presentation.action.search

import data.model.Meal
import domain.use_case.search.SearchMealsByNameUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class MealByNameAction(
    private val getByNameUseCase: SearchMealsByNameUseCase
) : MenuAction {
    override val description: String = "Search Meal by Name"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        try {
            val mealName = promptForMealName(ui, inputReader)
            displayMatchingMeals(ui, mealName)
        } finally {
            displaySeparator(ui)
        }
    }

    private fun promptForMealName(ui: UiExecutor, inputReader: InputReader): String {
        ui.displayPrompt("Enter the name of the meal: ")
        return inputReader.readString().trim()
    }

    private fun displayMatchingMeals(ui: UiExecutor, mealName: String) {
        getByNameUseCase.searchMealsByName(mealName)
            .onSuccess { meals -> handleMealsFound(ui, meals) }
            .onFailure { error -> handleSearchError(ui, error) }
    }

    private fun handleMealsFound(ui: UiExecutor, meals: List<Meal>) {
        if (meals.isEmpty()) {
            ui.displayResult("No meals found with that name.")
            return
        }

        meals.forEach { meal ->
            ui.displayResult("\nMeal found:")
            ui.displayResult(meal.toString())
        }
    }

    private fun handleSearchError(ui: UiExecutor, error: Throwable) {
        val errorMessage = error.message ?: "Failed to find meals due to an unknown error"
        ui.displayError(errorMessage)
    }
}