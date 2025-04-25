package domain.use_case.suggest

import data.meal.MealsRepository
import data.model.Meal

class SuggestEasyFoodUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getEasyFoodSuggestion(count: Int = RANDOM_NUMBER_TEN_BY_DEFAULT): List<Meal> {
        return mealsRepository.getAllMeals()
            .getRandomMeals(count)
            .ifEmpty { throw NoSuchElementException("No meals found") }
    }


    private fun List<Meal>.getRandomMeals(count: Int): List<Meal> {
        return filter(::isEasyMeal)
            .shuffled()
            .take(count)
    }


    private fun isEasyMeal(meal: Meal): Boolean {
        return meal.minutes <= MAX_PREP_TIME_MINUTES
                && meal.ingredientsCount <= INGREDIENTS_COUNT
                && meal.stepsCount <= STEPS_COUNT
    }


    private companion object {
        const val RANDOM_NUMBER_TEN_BY_DEFAULT = 10
        const val MAX_PREP_TIME_MINUTES = 30
        const val INGREDIENTS_COUNT = 5
        const val STEPS_COUNT = 6
    }
}