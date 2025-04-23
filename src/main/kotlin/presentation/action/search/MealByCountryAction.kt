package presentation.action.search

import domain.use_case.search.SearchFoodByCountryUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class MealByCountryAction(
    private val getMealsByCountryUseCase: SearchFoodByCountryUseCase
) : MenuAction {
    override val description: String = "Meals by Country"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayPrompt("Enter a country to discover their meals: ")
        val countryName = inputReader.readString().trim()
        val exploreMeals = getMealsByCountryUseCase.exploreMealsRelatedToCountry(countryName)

        try {
            if (exploreMeals.isEmpty()) {
                ui.displayResult("No meals found for '$countryName'.")
            } else {
                ui.displayResult("Meals from $countryName:")
                exploreMeals.forEachIndexed { index, meal ->
                    ui.displayResult("${index + 1} ${meal.name}")
                }
            }
        } catch (e: Exception) {
            ui.displayError(e.message ?: "Error fetching meals for country")
        }
        ui.displayResult("------------------------------------------------------------")
    }
}