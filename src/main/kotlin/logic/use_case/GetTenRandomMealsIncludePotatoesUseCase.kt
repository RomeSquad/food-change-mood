package logic.use_case

import logic.MealsRepository
import model.Meal

class GetTenRandomMealsIncludePotatoesUseCase(
    private val mealsRepository: MealsRepository,
) {
    fun getLimitRandomMealsIncludePotatoes(limit: Int = 10): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter(::onlyHighQualityData)
            .shuffled()
            .take(limit)
    }

    private fun onlyHighQualityData(meal: Meal): Boolean {
        return meal.ingredients.any { it.contains("potato", ignoreCase = true) }
    }
}
