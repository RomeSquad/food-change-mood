package presentation.action.search

import data.model.Meal
import domain.use_case.search.SearchFoodByCountryUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class MealByCountryAction(
    private val getMealsByCountryUseCase: SearchFoodByCountryUseCase
) : MenuAction {
    override val description: String = "Meals by Country"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        try {
            val countryName = promptForCountry(ui, inputReader)
            val meals = fetchMealsByCountry(countryName)
            displayCountryMeals(ui, countryName, meals)
        } finally {
            displaySeparator(ui)
        }
    }

    private fun promptForCountry(ui: UiExecutor, inputReader: InputReader): String {
        ui.displayPrompt("Enter a country to discover their meals: ")
        return inputReader.readString().trim()
    }

    private fun fetchMealsByCountry(countryName: String): List<Meal> {
        return try {
            getMealsByCountryUseCase.exploreMealsRelatedToCountry(countryName)
        } catch (e: Exception) {
            throw Exception("Error fetching meals for country: ${e.message}")
        }
    }

    private fun displayCountryMeals(ui: UiExecutor, countryName: String, meals: List<Meal>) {
        when {
            meals.isEmpty() -> ui.displayResult("No meals found for '$countryName'.")
            else -> {
                ui.displayResult("Meals from $countryName:")
                meals.forEachIndexed { index, meal ->
                    ui.displayResult("${index + 1}. ${meal.name}")
                }
            }
        }
    }
}