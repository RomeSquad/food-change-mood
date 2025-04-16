package logic.use_case

import logic.MealsRepository
import model.Meal

class IdentifyIraqiMealsUseCase (
    private val mealsRepository: MealsRepository
) {
    fun identifyIraqiMeals(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal ->
                checkIraqiTag(meal) || checkIraqDescription(meal)
            }
    }

    private fun checkIraqiTag(meal: Meal): Boolean {
        return meal.tags.toString().contains("iraqi", true)
    }

    private fun checkIraqDescription(meal: Meal): Boolean {
        return meal.description?.contains("Iraq", true) ?: false
    }
}