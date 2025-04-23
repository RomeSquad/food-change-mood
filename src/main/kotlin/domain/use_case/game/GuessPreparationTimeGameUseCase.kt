package domain.use_case.game

import data.meal.MealsRepository
import data.model.Meal

class GuessPreparationTimeGameUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getRandomMealWithPreparationTime(): Meal? {
        return mealsRepository.getAllMeals()
            .filter { it.minutes > 0 }
            .randomOrNull()
            ?: throw NoSuchElementException(
                "Cannot start GuessPreparationTimeGame – no meals found with preparation time"
            )
    }

    fun checkUserGuess(guess: Int, correctTime: Int): GuessResult {
        return when {
            guess == correctTime -> GuessResult.CORRECT
            guess < correctTime -> GuessResult.TOO_LOW
            else -> GuessResult.TOO_HIGH
        }
    }

    enum class GuessResult {
        CORRECT,
        TOO_LOW,
        TOO_HIGH
    }
}