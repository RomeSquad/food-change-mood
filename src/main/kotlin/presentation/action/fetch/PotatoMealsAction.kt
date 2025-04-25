package presentation.action.fetch

import domain.use_case.fetch.GetMealsContainsPotatoUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class PotatoMealsAction(
    private val getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase
) : MenuAction {
    override val description: String = "Potato Meals"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        displayPotatoMealsHeader(ui)
        displayPotatoMealsList(ui)
        displaySeparator(ui)
    }

    private fun displayPotatoMealsHeader(ui: UiExecutor) {
        ui.displayResult("=== Potato Meals ===")
    }

    private fun displayPotatoMealsList(ui: UiExecutor) {
        getMealsContainsPotatoUseCase.getMealsContainsPotato()
            .forEach { meal ->
                ui.displayResult(meal.toString())
            }
    }
}