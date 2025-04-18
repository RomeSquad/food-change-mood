package domain.use_case

import data.meal.MealsRepository
import model.Meal

class GetIraqiMealsUseCase (
    private val mealsRepository: MealsRepository
) {
    fun getIraqiMeals(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal ->
                checkIraqiTag(meal) || checkIraqDescription(meal)
            }
    }

    private fun checkIraqiTag(meal: Meal): Boolean {
        return meal.tags.toString().contains(IRAQI, true)
    }

    private fun checkIraqDescription(meal: Meal): Boolean {
        return meal.description?.contains(IRAQ, true) ?: false
    }

    companion object {
        private const val IRAQI = "iraqi"
        private const val IRAQ = "Iraq"
    }
}