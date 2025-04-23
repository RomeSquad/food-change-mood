package domain.use_case.suggest

import data.meal.MealsRepository
import data.utils.NoMealsFoundException
import model.Meal

class SuggestItalianFoodForGroupUseCase(
    private val mealsRepository: MealsRepository
) {
    fun suggestItalianMealsForLargeGroup(): List<Meal> =
        mealsRepository
            .getAllMeals()
            .filter(::onlyItalianFoodForLargeGroup)
            .takeIf { it.isNotEmpty() }
            ?: throw NoMealsFoundException(
                "No Italian meals found suitable for a large group"
            )

    private fun onlyItalianFoodForLargeGroup(meal: Meal): Boolean {
        return meal.tags.any { tag ->
            tag.equals(ITALIAN_FOOD_TAG, ignoreCase = true)
        } && meal.tags.any { tag ->
            tag.equals(LARGE_GROUP_TAG, ignoreCase = true)
        }
    }

    companion object {
        private const val LARGE_GROUP_TAG = "for-large-groups"
        private const val ITALIAN_FOOD_TAG = "italian"
    }
}