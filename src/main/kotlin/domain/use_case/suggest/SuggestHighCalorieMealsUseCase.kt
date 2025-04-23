package domain.use_case.suggest

import data.meal.MealsRepository
import data.model.Meal

class SuggestHighCalorieMealsUseCase(
    private val mealsRepository: MealsRepository,
) {
    private val meals = getMealsContainsHighCalories()

    fun getMealsContainsHighCalories(
        calories: Double = 700.0
    ): MutableList<Meal> {
        val allMeals = mealsRepository.getAllMeals()
        val filteredMeals = filterMealsByMinCalories(allMeals, calories)

        if (filteredMeals.isEmpty()) {
            throw IllegalArgumentException("No meals found with calories greater than $calories")
        }
        return filteredMeals.shuffled().toMutableList()
    }

    private fun filterMealsByMinCalories(
        meals: List<Meal>,
        minCalories: Double
    ): List<Meal> {
        return meals.filter { it.nutrition.calories > minCalories }
    }

    fun getNextMeal(): Meal {
        if (meals.isEmpty()) {
            throw NoSuchElementException("No more meals available")
        }
        return meals.removeAt(0)
    }
}