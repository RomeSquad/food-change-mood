package domain.use_case.fetch

import data.meal.MealsRepository
import model.Meal

class GetIraqiMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getIraqiMeals(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal -> isIraqiMeal(meal) }
    }

    private fun isIraqiMeal(meal: Meal): Boolean {
        return meal.tags.toString().contains(IRAQI, true) || meal.description?.contains(IRAQ, true) == true
    }

    companion object {
        private const val IRAQI = "iraqi"
        private const val IRAQ = "Iraq"
    }

}