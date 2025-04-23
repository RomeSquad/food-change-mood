package presentation.action.game

import domain.use_case.game.IngredientGameUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class IngredientGameAction(
    private val ingredientGameUseCase: IngredientGameUseCase
) : MenuAction {
    override val description: String = "Ingredient Guessing Game"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        while (ingredientGameUseCase
                .getNextQuestion() != null
        ) {
            val question = ingredientGameUseCase.getNextQuestion()
            if (question == null) {
                ui.displayResult("No more questions available for the game.")
                break
            }

            ui.apply {
                displayResult("Guess the ingredient from the list of ingredients")
                displayResult("Question -> mealName: ${question.mealName}\noptions(${question.options})")
                displayPrompt("Enter Answer: ")
            }

            val answer = inputReader.readString()

            if (ingredientGameUseCase.submitAnswer(answer, question.correctAnswer)) {
                ingredientGameUseCase.getScore()
                ui.displayResult("Current Score: ${ingredientGameUseCase.getScore()} points")
            } else {
                ui.apply {
                    displayResult("Please, Try again 😐")
                    displayResult("Current Score: ${ingredientGameUseCase.getScore()} points")
                }
                break
            }
        }
    }
}