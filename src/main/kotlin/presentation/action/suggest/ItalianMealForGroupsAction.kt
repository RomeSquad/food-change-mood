package presentation.action.suggest

import domain.use_case.suggest.SuggestItalianFoodForGroupUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class ItalianMealForGroupsAction(
    private val getItalianMealsForLargeGroupsUseCase: SuggestItalianFoodForGroupUseCase
) : MenuAction {
    override val description: String = "Italian Meals for Large Groups"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        getItalianMealsForLargeGroupsUseCase.suggestItalianMealsForLargeGroup().forEachIndexed { index, meal ->
            ui.displayResult("${index + 1}. ${meal.name}")
        }
        ui.displayResult("------------------------------------------------------------")
    }
}