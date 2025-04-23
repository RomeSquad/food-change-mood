package presentation.action.game

import data.model.ingredient_game.Question
import domain.use_case.game.IngredientGameUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class IngredientGameAction(
    private val ingredientGameUseCase: IngredientGameUseCase
) : MenuAction {
    override val description: String = "Ingredient Guessing Game"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        while (hasMoreQuestions()) {
            val question = ingredientGameUseCase.getNextQuestion() ?: break

            displayQuestion(ui, question)
            val answer = inputReader.readString()

            if (!processAnswer(answer, question, ui)) {
                break
            }
            displaySeparator(ui)
        }
    }

    private fun hasMoreQuestions(): Boolean {
        return ingredientGameUseCase.getNextQuestion() != null
    }

    private fun displayQuestion(ui: UiExecutor, question: Question) {
        ui.apply {
            displayResult("Guess the ingredient from the list of ingredients")
            displayResult("Question -> mealName: ${question.mealName}\noptions(${question.options})")
            displayPrompt("Enter Answer: ")
        }
    }

    private fun processAnswer(answer: String, question: Question, ui: UiExecutor): Boolean {
        return if (ingredientGameUseCase.submitAnswer(answer, question.correctAnswer)) {
            displayCorrectAnswerFeedback(ui)
            true
        } else {
            displayWrongAnswerFeedback(ui)
            false
        }
    }

    private fun displayCorrectAnswerFeedback(ui: UiExecutor) {
        ui.displayResult("Current Score: ${ingredientGameUseCase.getScore()} points")
    }

    private fun displayWrongAnswerFeedback(ui: UiExecutor) {
        ui.apply {
            displayResult("Please, Try again 😐")
            displayResult("Current Score: ${ingredientGameUseCase.getScore()} points")
        }
    }
}