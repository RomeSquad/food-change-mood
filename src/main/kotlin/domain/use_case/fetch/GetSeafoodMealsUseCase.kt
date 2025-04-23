package domain.use_case.fetch

import data.meal.MealsRepository
import model.Meal
import model.seafood_meals.SeafoodMeal

class GetSeafoodMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getSeafoodMeals(): List<SeafoodMeal> =
        mealsRepository.getAllMeals()
            .filter(::onlyContainSeafoodMeal)
            .takeIf { it.isNotEmpty() }
            ?.sortedByDescending { it.nutrition.protein }
            ?.map {
                SeafoodMeal(
                    name = it.name,
                    protein = it.nutrition.protein
                )
            }
            ?: throw NoSuchElementException("No seafood meals based on protein found")

    private fun onlyContainSeafoodMeal(meal: Meal): Boolean =
        meal.tags.any { it.contains(TAG_NAME, ignoreCase = true) }

    companion object {
        const val TAG_NAME = "seafood"
    }
}