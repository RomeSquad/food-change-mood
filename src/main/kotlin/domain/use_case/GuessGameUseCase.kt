package domain.use_case
import data.meal.MealsRepository
import model.Meal

class GuessGameUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getRandomGuessableMeal(): Meal? {
        val meals = mealsRepository.getAllMeals()
            .filter { it.minutes > 0 }

        return meals.takeIf { it.isNotEmpty() }?.random()
    }

    fun evaluateGuess(userGuess: Int, correctTime: Int): GuessResult {
        return when {
            userGuess == correctTime -> GuessResult.CORRECT
            userGuess < correctTime -> GuessResult.TOO_LOW
            else -> GuessResult.TOO_HIGH
        }
    }

    enum class GuessResult {
        CORRECT, TOO_LOW, TOO_HIGH
    }
}