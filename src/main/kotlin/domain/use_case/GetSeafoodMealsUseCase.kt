package domain.use_case

import data.meal.MealsRepository
import model.seafood_meals.SeafoodMeal

class GetSeafoodMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getSeafoodMeals(): List<SeafoodMeal> =
        mealsRepository.getAllMeals()
            .filter { it.tags.any { tag -> tag.contains(TAG_NAME, ignoreCase = true) } == true }
            .takeIf { it.isNotEmpty() }
            ?.sortedByDescending { it.nutrition.protein }
            ?.map {
                SeafoodMeal(
                    name = it.name,
                    protein = it.nutrition.protein
                )
            }
            ?: throw NoSuchElementException("No seafood meals based on protein found")

    companion object {
        const val TAG_NAME = "seafood"
    }
}