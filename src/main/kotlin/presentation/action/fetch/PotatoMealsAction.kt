package presentation.action.fetch

import domain.use_case.fetch.GetMealsContainsPotatoUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class PotatoMealsAction(
    private val getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase
) : MenuAction {
    override val description: String = "Potato Meals"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("=== Potato Meals ===")
        val potatoMeals = getMealsContainsPotatoUseCase.getMealsContainsPotato()
        potatoMeals.forEach { meal ->
            ui.displayResult(meal)
        }
        ui.displayResult("-------------------------------------------------------")
    }
}