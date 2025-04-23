package presentation.action.suggest

import domain.use_case.suggest.SuggestEasyFoodUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class EasyFoodSuggestionAction(
    private val getRandomMealsUseCase: SuggestEasyFoodUseCase
) : MenuAction {
    override val description: String = "Easy Food Suggestion Game"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("\nEasy Food Suggestion Game, TEN RANDOM MEALS BY DEFAULT:")
        getRandomMealsUseCase.getEasyFoodSuggestion().forEach { meal ->
            ui.displayResult("\nMeal Name is: ${meal.name}\n")
        }

        ui.displayPrompt("\nEnter number of meals you want: ")
        val count = inputReader.readIntOrNull()

        if (count != null) {
            getRandomMealsUseCase.getEasyFoodSuggestion(count).forEach { meal ->
                ui.displayResult("\nMeal Name: ${meal.name}\n")
            }
        }
        ui.displayResult("------------------------------------------------------------")
    }
}