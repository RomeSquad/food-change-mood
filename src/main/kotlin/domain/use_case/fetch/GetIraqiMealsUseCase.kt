package domain.use_case.fetch

import data.meal.MealsRepository
import data.model.Meal

class GetIraqiMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getIraqiMeals(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal ->
                 checkIraqiTag(meal) || checkIraqDescription(meal)
            }
    }

    private fun checkIraqiTag (
        meal: Meal,
        tag: String = "iraqi"
    ): Boolean {
        return meal.tags.toString().contains(tag, true)
    }

    private fun checkIraqDescription (
        meal: Meal,
        description: String = "Iraq"
    ): Boolean {
        return meal.description?.contains(description, true) ?: false
    }
}