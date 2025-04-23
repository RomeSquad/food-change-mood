package presentation.action.search

import data.model.Meal
import domain.use_case.search.SearchMealsByDateUseCase
import domain.use_case.search.SearchMealsByIdUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class MealByDateAction(
    private val getByDateUseCase: SearchMealsByDateUseCase
) : MenuAction {
    override val description: String = "Meals by Date"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        try {
            val date = promptForDate(ui, inputReader)
            val meals = searchMealsByDate(date)

            if (meals.isEmpty()) {
                ui.displayResult("No meals found for date $date")
                return
            }

            displayMealsTable(ui, date, meals)
            promptForMealDetails(ui, inputReader, meals)
        } finally {
            displaySeparator(ui)
        }
    }

    private fun promptForDate(ui: UiExecutor, inputReader: InputReader): String {
        ui.displayPrompt("Enter the date (yyyy-mm-dd): ")
        return inputReader.readString().trim()
    }

    private fun searchMealsByDate(date: String): List<Meal> {
        return getByDateUseCase.searchMealsByDate(date)
    }

    private fun displayMealsTable(ui: UiExecutor, date: String, meals: List<Meal>) {
        ui.displayResult("Meals for date $date:\n")
        ui.displayResult(" ---------------------------------- ")
        ui.displayResult("|      ID       |      Name       |")
        ui.displayResult(" ---------------------------------- ")

        meals.forEach { meal ->
            ui.displayResult("| ${meal.id.toString().padEnd(13)} | ${meal.name.padEnd(15)} |")
        }

        ui.displayResult(" ---------------------------------- ")
    }

    private fun promptForMealDetails(ui: UiExecutor, inputReader: InputReader, meals: List<Meal>) {
        ui.displayPrompt("Enter the meal ID to get more details (or 'q' to skip): ")
        val input = inputReader.readString().trim()

        if (input.equals("q", ignoreCase = true)) return

        try {
            val meal = SearchMealsByIdUseCase().getById(input, meals)
            displayMealDetails(ui, meal)
        } catch (e: Exception) {
            ui.displayError("Error: ${e.message ?: "Invalid meal ID"}")
        }
    }

    private fun displayMealDetails(ui: UiExecutor, meal: Meal) {
        ui.displayResult("\nMeal details:")
        ui.displayResult("----------------------------------")
        ui.displayResult(meal.toString())
        ui.displayResult("----------------------------------")
    }
}