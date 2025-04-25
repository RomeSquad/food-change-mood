package presentation.action.suggest

import data.model.Meal
import domain.use_case.suggest.SuggestItalianFoodForGroupUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class ItalianMealForGroupsAction(
    private val getItalianMealsForLargeGroupsUseCase: SuggestItalianFoodForGroupUseCase
) : MenuAction {
    override val description: String = "Italian Meals for Large Groups"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        try {
            displayItalianMealsForGroups(ui)
        } finally {
            displaySeparator(ui)
        }
    }

    private fun displayItalianMealsForGroups(ui: UiExecutor) {
        val meals = fetchItalianMealsForGroups()

        if (meals.isEmpty()) {
            ui.displayResult("No Italian meals available for large groups.")
        } else {
            displayMealList(ui, meals)
        }
    }

    private fun fetchItalianMealsForGroups(): List<Meal> {
        return getItalianMealsForLargeGroupsUseCase.suggestItalianMealsForLargeGroup()
    }

    private fun displayMealList(ui: UiExecutor, meals: List<Meal>) {
        meals.forEachIndexed { index, meal ->
            displayNumberedMeal(ui, index, meal)
        }
    }

    private fun displayNumberedMeal(ui: UiExecutor, position: Int, meal: Meal) {
        ui.displayResult("${position + 1}. ${meal.name}")
    }

}