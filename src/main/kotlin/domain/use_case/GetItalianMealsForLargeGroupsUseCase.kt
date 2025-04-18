package domain.use_case

import data.meal.MealsRepository
import model.Meal

class GetItalianMealsForLargeGroupsUseCase (
    private val mealsRepository: MealsRepository
) {
    fun getItalianMealsForLargeGroups(): List<Meal> {
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
        private const val LARGE_GROUPS = "for-large-groups"
        private const val ITALI = "itali"
    }
}