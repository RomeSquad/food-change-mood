package presentation.action.game

import data.model.Meal
import domain.use_case.game.GuessPreparationTimeGameUseCase
import presentation.MenuAction
import presentation.displaySeparator
import presentation.io.InputReader
import presentation.io.UiExecutor

class PreparationTimeGuessingAction (
    private val guessGameUseCase: GuessPreparationTimeGameUseCase
) : MenuAction {
    override val description: String = "Preparation Time Guessing Game"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        guessGameUseCase.getRandomMealWithPreparationTime()?.let { meal ->
            playGuessingGame(ui, inputReader, meal)
        } ?: ui.displayResult("No meals suitable for the game.")

        displaySeparator(ui)
    }

    private fun playGuessingGame(ui: UiExecutor, inputReader: InputReader, meal: Meal) {
        val correctTime = meal.minutes
        displayGameHeader(ui, meal)

        repeat(3) { attempt ->
            if (!processUserAttempt(ui, inputReader, correctTime, attempt)) return
        }

        displayFinalAnswer(ui, correctTime)
    }

    private fun displayGameHeader(ui: UiExecutor, meal: Meal) {
        ui.apply {
            displayResult("---------- Guess Game ----------")
            displayResult("Name of the meal: ${meal.name}")
            displayResult("You have 3 attempts to guess the preparation time (in minutes):")
        }
    }

    private fun processUserAttempt(
        ui: UiExecutor,
        inputReader: InputReader,
        correctTime: Int,
        attempt: Int
    ): Boolean {
        ui.displayPrompt("Attempt ${attempt + 1}: ")
        val guess = inputReader.readIntOrNull()

        if (guess == null) {
            ui.displayResult("Please enter a valid number.")
            return true
        }

        return when (guessGameUseCase.checkUserGuess(guess, correctTime)) {
            GuessPreparationTimeGameUseCase.GuessResult.CORRECT -> {
                displayCorrectGuess(ui, correctTime)
                false
            }

            GuessPreparationTimeGameUseCase.GuessResult.TOO_LOW -> {
                ui.displayResult("Less than the correct time.")
                true
            }

            GuessPreparationTimeGameUseCase.GuessResult.TOO_HIGH -> {
                ui.displayResult("More than the correct time.")
                true
            }
        }
    }

    private fun displayCorrectGuess(ui: UiExecutor, correctTime: Int) {
        ui.displayResult("Correct answer! Preparation time is $correctTime minutes")
    }

    private fun displayFinalAnswer(ui: UiExecutor, correctTime: Int) {
        ui.displayResult("Attempts are over. Correct time is: $correctTime minutes.")
    }
}