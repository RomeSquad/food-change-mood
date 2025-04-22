package domain.use_case
import data.meal.MealsRepository
import model.Meal

class GuessGameUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getRandomMealWithPreparationTime(): Meal? {
        return mealsRepository.getAllMeals()
            .filter { it.minutes > 0 }
            .takeIf { it.isNotEmpty() }
            ?.random()
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