package domain.use_case.search

import data.meal.MealsRepository
import model.Meal

class SearchFoodByCountryUseCase(
    private val mealsRepository: MealsRepository
) {

    fun exploreMealsRelatedToCountry(countryName: String, limit: Int = 20): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal ->
                onlyMealRelatedToCountry(meal, countryName)
            }
            .takeIf { it.isNotEmpty() }
            ?.take(limit)
            ?: throw NoSuchElementException("No meals found related to country: $countryName")
    }

    private fun onlyMealRelatedToCountry(meal: Meal, country: String): Boolean {
        return meal.tags.any { tag ->
            tag.contains(country, ignoreCase = true)
        }
                || meal.description?.contains(country, ignoreCase = true) ?: false
                || meal.name.contains(country, ignoreCase = true)
                || meal.steps.any { it.contains(country) }
                || meal.ingredients.any { it.contains(country) }
    }
}