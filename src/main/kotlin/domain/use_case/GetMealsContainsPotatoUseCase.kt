package domain.use_case

import data.meal.MealsRepository
import domain.utils.getRandomEasyMeals
import model.Meal

class GetMealsContainsPotatoUseCase(
    private val mealsRepository: MealsRepository,
) {
    fun getMealsContainsPotato(limit: Int = 10): List<String> =
        mealsRepository.getAllMeals()
            .filter(
                ::onlyContainsPotatoIngredient
            )
            .takeIf { it.isNotEmpty() }
            ?.shuffled()
            ?.getRandomEasyMeals(limit)
            ?.map { it.name }
            ?: throw NoSuchElementException("No meals found that contain potato")


    private fun onlyContainsPotatoIngredient(meal: Meal): Boolean {
        return meal.ingredients.any {
            it.lowercase().contains(POTATO_INGREDIENT)
        }
    }

    private companion object {
        const val POTATO_INGREDIENT = "potato"
    }
}
