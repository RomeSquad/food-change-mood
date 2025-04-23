package domain.use_case.suggest

import data.meal.MealsRepository
import data.model.Meal

class SuggestEasyFoodUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getEasyFoodSuggestion(count: Int = RANDOM_NUMBER): List<Meal> =
        mealsRepository.getAllMeals().getRandomMeals(count)

    fun List<Meal>.getRandomMeals(count: Int): List<Meal> =
        this.filter(::isEasyMeal)
            .shuffled()
            .take(count)


    fun isEasyMeal(meal: Meal): Boolean =
        meal.minutes <= MAX_PREP_TIME_MINUTES
                && meal.ingredientsCount <= INGREDIENTS_COUNT
                && meal.stepsCount <= STEPS_COUNT

    private companion object {
        const val RANDOM_NUMBER = 10
        const val MAX_PREP_TIME_MINUTES = 30
        const val INGREDIENTS_COUNT = 5
        const val STEPS_COUNT = 6
    }
}