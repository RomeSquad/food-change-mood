package domain.use_case

import data.meal.MealsRepository
import model.Meal

class GetMealsByCountryUseCase (

    private val mealsRepository: MealsRepository
) {
    val countryChecker : List<(Meal, String) -> Boolean> =  listOf(
        ::checkCountriesInName,
        ::checkCountriesInTags,
        ::checkCountriesInSteps,
        ::checkCountriesInDescription,
        ::checkCountriesIngredients
    )


    fun getLimitRandomMealsRelatedToCountry(countryName : String ,limit : Int= 20): List<Meal> {
        if (countryName.isBlank()) return emptyList()
        return mealsRepository.getAllMeals()
            .filter {meal -> isMealRelatedToCountry(meal , countryName)
            }
            .shuffled()
            .take(limit)
    }

    private fun isMealRelatedToCountry(meal: Meal,countryName:String): Boolean {
        return countryChecker.any { checker -> checker(meal,countryName)  }
    }

    private fun checkCountriesInName(meal: Meal,countryName:String): Boolean {
        return meal.name.contains(countryName,ignoreCase = true)
    }

    private fun checkCountriesInTags(meal: Meal,countryName:String): Boolean {
        return meal.tags.any{ it.contains(countryName,ignoreCase = true) }
    }

    private fun checkCountriesInSteps(meal: Meal,countryName:String): Boolean {
        return meal.steps.any{ it.contains(countryName,ignoreCase = true) }
    }

    private fun checkCountriesInDescription(meal: Meal,countryName:String): Boolean {
        return meal.description?.contains(countryName,ignoreCase = true) ?: false
    }

    private fun checkCountriesIngredients(meal: Meal,countryName:String): Boolean {
        return meal.ingredients.any{it.contains(countryName,ignoreCase = true) }
    }
}