package domain.use_case

import data.meal.MealsRepository
import model.Meal

class GetRankedSeafoodByProteinUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getSeafoodMealsSortedByProtein(): List<String> {
        val allMeals = mealsRepository.getAllMeals()

        val seafoodMeals = allMeals.filter(::isSeafoodMeals)
        val sortedSeafoodMeals = seafoodMeals
            .sortedByDescending { it.nutrition.protein }
        return sortedSeafoodMeals.mapIndexed { index, meal ->
            val rank = index + 1
            "Rank: $rank - ${meal.name}: ${meal.nutrition.protein}g protein"
        }
    }

    private fun isSeafoodMeals(meal: Meal): Boolean {
        return meal.ingredients.any { it.contains("Seafood", ignoreCase = true) }
    }
}