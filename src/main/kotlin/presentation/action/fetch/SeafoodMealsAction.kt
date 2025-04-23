package presentation.action.fetch

import domain.use_case.fetch.GetSeafoodMealsUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class SeafoodMealsAction(
    private val getRankedSeafoodByProteinUseCase: GetSeafoodMealsUseCase
) : MenuAction {
    override val description: String = "Seafood Meals Sorted by Protein"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("--- Seafood Meals Sorted by Protein (Highest First) ---")
        val rankedSeafoodMeals = getRankedSeafoodByProteinUseCase.getSeafoodMeals()
        rankedSeafoodMeals.forEachIndexed { index, seafoodMeal ->
            ui.displayResult("${index + 1}. ${seafoodMeal.name} - Protein: ${seafoodMeal.protein}g")
        }
        ui.displayResult("-------------------------------------------------------")
    }
}