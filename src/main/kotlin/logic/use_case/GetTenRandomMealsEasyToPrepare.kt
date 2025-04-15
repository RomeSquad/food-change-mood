package logic.use_case

import logic.MealsRepository
import model.Meal

class GetTenRandomEasyMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getTenRandomEasyMeals(): List<String> {
        return mealsRepository.getAllMeals()
            .filter(::isEasyMeal)
            .take(10)
            .shuffled()
            .map { it.name }
    }

    private fun isEasyMeal(meal: Meal): Boolean =
        meal.minutes <= 30 && meal.nIngredients <= 5 && meal.nSteps <= 6

}