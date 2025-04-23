package presentation.action.search

import domain.use_case.search.SearchMealsByDateUseCase
import domain.use_case.search.SearchMealsByIdUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class MealByDateAction(
    private val getByDateUseCase: SearchMealsByDateUseCase
) : MenuAction {
    override val description: String = "Meals by Date"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayPrompt("Enter the date (yyyy-mm-dd): ")
        val date = inputReader.readString()
        val resultMeals = getByDateUseCase.searchMealsByDate(date)

        resultMeals.forEach { meal ->
            ui.displayResult("Meals for date $date:\n")
            ui.displayResult(" ---------------------------------- ")
            ui.displayResult("|      ID       |         Name      ")
            ui.displayResult(" ---------------------------------- ")
            ui.displayResult("|      ${meal.id}      |     ${meal.name}   ")
        }

        ui.displayPrompt("Enter the meal ID to get more details (or 'q' to skip): ")
        val mealId = inputReader.readString()
        if (mealId != "q") {
            val mealResult = SearchMealsByIdUseCase().getById(mealId, resultMeals)
            mealResult.let { ui.displayResult("Meal details:\n$it") }
        }
        ui.displayResult("------------------------------------------------------------")
    }
}