package presentation.action.game

import domain.use_case.game.GuessPreparationTimeGameUseCase
import presentation.MenuAction
import presentation.io.InputReader
import presentation.io.UiExecutor

class PreparationTimeGuessingAction (
    private val guessGameUseCase: GuessPreparationTimeGameUseCase
) : MenuAction {
    override val description: String = "Preparation Time Guessing Game"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        guessGameUseCase.getRandomMealWithPreparationTime()
            ?.let { meal ->
                val correctTime = meal.minutes
                ui.apply {
                    displayResult("---------- Guess Game ----------")
                    displayResult("Name of the meal: ${meal.name}")
                    displayResult("You have 3 attempts to guess the preparation time (in minutes):")
                }

                repeat(3) { attempt ->
                    ui.displayPrompt("Attempt ${attempt + 1}: ")
                    val guess = inputReader.readIntOrNull()

                    if (guess == null) {
                        ui.displayResult("Please enter a valid number.")
                        return@repeat
                    }

                    when (guessGameUseCase.checkUserGuess(guess, correctTime)) {
                        GuessPreparationTimeGameUseCase.GuessResult.CORRECT -> {
                            ui.displayResult("Correct answer! Preparation time is $correctTime minutes")
                            return@let
                        }

                        GuessPreparationTimeGameUseCase.GuessResult.TOO_LOW -> ui.displayResult("Less than the correct time.")
                        GuessPreparationTimeGameUseCase.GuessResult.TOO_HIGH -> ui.displayResult("More than the correct time.")
                    }
                }

                ui.displayResult("Attempts are over. Correct time is: $correctTime minutes.")
            } ?: ui.displayResult("No meals suitable for the game.")
        ui.displayResult("------------------------------------------------------------")
    }
}