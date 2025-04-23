package presentation.action.fetch

import domain.use_case.fetch.GetIraqiMealsUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class IraqiMealsAction(
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase
) : MenuAction {
    override val description: String = "Iraqi Meals"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        getIraqiMealsUseCase.getIraqiMeals().forEach { meal ->
            ui.displayResult(meal.toString())
        }
        ui.displayResult("------------------------------------------------------------")
    }
}