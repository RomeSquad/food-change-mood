package domain.use_case.suggest

import data.meal.MealsRepository
import data.model.Meal

class SuggestHighCalorieMealsUseCase(
    private val mealsRepository: MealsRepository,
) {
    private val meals = getMealsContainsHighCalories()

    private fun getMealsContainsHighCalories(
        minCalories: Double = 700.0
    ): MutableList<Meal> {
        val filteredMeals = mealsRepository.getAllMeals().filter { it.nutrition.calories > minCalories }

        if (filteredMeals.isEmpty()) {
            throw IllegalArgumentException("No meals found with calories greater than $minCalories")
        }
        return filteredMeals.shuffled().toMutableList()
    }

    fun getNextMeal(): Meal {
        if (meals.isEmpty()) {
            throw NoSuchElementException("No more meals available")
        }
        return meals.removeAt(0)
    }
}