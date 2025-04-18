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
        return meal.tags.toString().contains(IRAQI, true)
    }

    private fun checkIraqDescription(meal: Meal): Boolean {
        return meal.description?.contains(IRAQ, true) ?: false
    }

    companion object {
        const val IRAQI = "iraqi"
        const val IRAQ = "Iraq"
    }
}