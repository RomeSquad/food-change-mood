package domain.use_case.fetch

import data.meal.MealsRepository
import data.model.Meal
import domain.NoMealsFoundException

class GetMealsContainsPotatoUseCase(
    private val mealsRepository: MealsRepository,
) {
    fun getMealsContainsPotato(limit: Int = 10): List<String> {
        val potatoMeals = mealsRepository.getAllMeals()
            .filter(::onlyContainsPotatoIngredient)

        if (potatoMeals.isEmpty()) {
            throw NoMealsFoundException("No meals found that contain potato")
        }

        return potatoMeals
            .shuffled()
            .take(limit)
            .map { it.name }
    }

    private fun onlyContainsPotatoIngredient(meal: Meal): Boolean {
        return meal.ingredients.any {
            it.lowercase().contains(POTATO_INGREDIENT)
        }
    }

    private companion object {
        const val POTATO_INGREDIENT = "potato"
    }
}