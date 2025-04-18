package domain.use_case

import data.meal.MealsRepository
import model.Meal

class GetHealthyMealsFilterUseCase (
    private val mealsRepository: MealsRepository
) {
    fun getHealthyFastMeals(): List<Meal> {
        val totalFats = mealsRepository.getAllMeals().map { it.nutrition.totalFat }
        val saturatedFats = mealsRepository.getAllMeals().map { it.nutrition.saturatedFat }
        val carbohydrates = mealsRepository.getAllMeals().map { it.nutrition.carbohydrates }

        val fatThreshold = quartile(totalFats)
        val saturatedThreshold = quartile(saturatedFats)
        val carbsThreshold = quartile(carbohydrates)


        return mealsRepository.getAllMeals().filter {
            it.minutes <= 15 &&
                    it.nutrition.totalFat <= fatThreshold &&
                    it.nutrition.saturatedFat <= saturatedThreshold &&
                    it.nutrition.carbohydrates <= carbsThreshold
        }
    }
    private fun quartile(values: List<Double>): Double {
        return values.sorted()[values.size / 4]
    }
}