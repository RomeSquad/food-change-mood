package domain.use_case

import data.meal.MealsRepository
import model.Meal

class GetLimitRandomMealsIncludePotatoesUseCase(
    private val mealsRepository: MealsRepository,
) {
    fun getLimitRandomMealsIncludePotatoes(limit: Int = 10): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter(::isPotatoesMeals)
            .shuffled()
            .take(limit)
    }

    private fun isPotatoesMeals(meal: Meal): Boolean {
        return meal.ingredients.any { it.contains("potato", ignoreCase = true) }
    }
}
