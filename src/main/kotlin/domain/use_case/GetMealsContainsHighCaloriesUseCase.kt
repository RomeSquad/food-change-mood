package domain.use_case

import data.meal.MealsRepository
import model.Meal

class GetMealsContainsHighCaloriesUseCase(
    private val mealsRepository: MealsRepository,
) {
    private val meals = getMealsContainsHighCalories()

    fun getMealsContainsHighCalories(
        calories: Double = 700.0
    ): MutableList<Meal> {
        val meals = mealsRepository.getAllMeals()
        val filteredMeals = meals.filter { it.nutrition.calories > calories }

        if (filteredMeals.isEmpty()) {
            throw Exception("No meals found with calories greater than $calories")
        }
        return filteredMeals.shuffled().toMutableList()
    }

    fun getNextMeal(): Meal {
        if (meals.isEmpty()) {
            throw Exception("No more meals available")
        }
        return meals.removeAt(0)
    }


}