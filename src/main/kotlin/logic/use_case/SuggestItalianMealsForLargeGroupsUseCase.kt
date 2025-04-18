package logic.use_case

import logic.MealsRepository
import model.Meal

class SuggestItalianMealsForLargeGroupsUseCase (
    private val mealsRepository: MealsRepository
) {
    fun suggestItalianMealsForLargeGroups(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal ->
                checkForLargeGroupsInTag(meal) && (checkItalianMealsInTag(meal) || checkItalianMealsInDescription(meal))
            }
    }

    private fun checkForLargeGroupsInTag(meal: Meal): Boolean {
        return meal.tags.toString().contains(LARGE_GROUPS)
    }

    private fun checkItalianMealsInTag(meal: Meal): Boolean {
        return meal.tags.toString().contains(ITALI)
    }

    private fun checkItalianMealsInDescription(meal: Meal): Boolean {
        return meal.description?.contains(ITALI) ?: false
    }

    companion object {
        const val LARGE_GROUPS = "for-large-groups"
        const val ITALI = "itali"
    }
}