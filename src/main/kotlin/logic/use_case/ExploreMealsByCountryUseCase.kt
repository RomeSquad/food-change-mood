package logic.use_case

import logic.MealsRepository
import model.Meal

class ExploreMealsByCountryUseCase (
    private val mealsRepository: MealsRepository
) {

    fun getLimitRandomMealsRelatedToCountry(countryName : String ): List<Meal> {
        return mealsRepository.getAllMeals() //related to that country
            .filter { it.tags.toString().contains(countryName)}
            .shuffled()
            .take(20)
    }
}