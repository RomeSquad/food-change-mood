package logic.use_case

import logic.MealsRepository
import model.Meal

class ExploreMealsByCountryUseCase (
    private val mealsRepository: MealsRepository
) {

    fun getLimitRandomMealsRelatedToCountry(countryName : String ): List<Meal> {
        return mealsRepository.getAllMeals() //related to that country
            .filter { meal ->
                checkCountriesInName(meal,countryName)|| checkCountriesInTags(meal,countryName) || checkCountriesInSteps(meal,countryName) ||  checkCountriesInDescription(meal,countryName) || checkCountriesIngredients(meal,countryName)
            }
            .shuffled()
            .take(20)
    }

    private fun checkCountriesInName(meal: Meal,countryName:String): Boolean {
        return meal.name.contains(countryName)
    }

    private fun checkCountriesInTags(meal: Meal,countryName:String): Boolean {
        return meal.tags.contains(countryName)
    }

    private fun checkCountriesInSteps(meal: Meal,countryName:String): Boolean {
        return meal.steps.contains(countryName)
    }

    private fun checkCountriesInDescription(meal: Meal,countryName:String): Boolean {
        return meal.description?.contains(countryName) ?: false
    }

    private fun checkCountriesIngredients(meal: Meal,countryName:String): Boolean {
        return meal.ingredients.contains(countryName) ?: false
    }

}