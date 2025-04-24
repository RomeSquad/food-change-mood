package domain.use_case.suggest

import data.meal.MealsRepository
import domain.NoMealsFoundException
import data.model.Meal

class SuggestItalianFoodForGroupUseCase(
    private val mealsRepository: MealsRepository
) {
    fun suggestItalianMealsForLargeGroup(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal ->
                checkForLargeGroupsTag(meal)
                        && (checkItalianMealsInTag(meal) || checkItalianMealsInDescription(meal))
            }
            .takeIf { it.isNotEmpty() }
            ?: throw NoMealsFoundException("No Italian meals found suitable for a large group")
    }

    private fun checkForLargeGroupsTag(
        meal: Meal,
        tag: String = "for-large-groups"
    ): Boolean {
        return meal.tags.toString().contains(tag,true)
    }

    private fun checkItalianMealsInTag(
        meal: Meal,
        tag: String = "italian"
    ): Boolean {
        return meal.tags.toString().contains(tag, true)
    }

    private fun checkItalianMealsInDescription(
        meal: Meal,
        description: String = "italian"
    ): Boolean {
        return meal.description?.contains(description, true) ?: false
    }
}