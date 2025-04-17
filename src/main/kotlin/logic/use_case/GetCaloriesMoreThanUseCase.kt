package logic.use_case

import logic.MealsRepository
import model.Meal

class GetCaloriesMoreThanUseCase(
    private val mealsRepository: MealsRepository,
) {
    fun getCaloriesMoreThan(
        calories: Double
    ): Result<List<Meal>> {
        return try {
            val meals = mealsRepository.getAllMeals()
            val filteredMeals = meals.filter { it.nutrition.calories > calories }
            if (filteredMeals.isEmpty()) {
                return Result.failure(Exception("No meals found with calories greater than $calories"))
            }
            Result.success(filteredMeals.map { it })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}