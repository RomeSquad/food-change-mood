package domain.use_case.fetch

import data.meal.MealsRepository
import data.model.Meal
import data.model.seafood_meals.SeafoodMeal
import domain.NoMealsFoundException

class GetSeafoodMealsUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getSeafoodMeals(): List<SeafoodMeal> {
        val seafoodMeals = mealsRepository.getAllMeals()
            .filter(::isSeafoodMeal)
            .sortedByDescending { it.nutrition.protein }

        if (seafoodMeals.isEmpty()) {
            throw NoMealsFoundException("No seafood meals found")
        }

        return seafoodMeals.mapIndexed { index, meal ->
            SeafoodMeal(
                name = meal.name,
                proteinPerGram = meal.nutrition.protein
            )
        }

    }

    private fun isSeafoodMeal(meal: Meal): Boolean {
        return meal.tags.any { it.contains(SEAFOOD_TAG, ignoreCase = true) }
    }

    companion object {
        private const val SEAFOOD_TAG = "seafood"
    }
}
