package domain.use_case

import model.Meal

class HealthyMealsFilterUseCase {
    fun getHealthyFastMeals(meals: List<Meal>): List<Meal> {
        val totalFats = meals.map { it.nutrition.totalFat }
        val saturatedFats = meals.map { it.nutrition.saturatedFat }
        val carbohydrates = meals.map { it.nutrition.carbohydrates }

        val fatThreshold = quartile(totalFats)
        val saturatedThreshold = quartile(saturatedFats)
        val carbsThreshold = quartile(carbohydrates)


        return meals.filter {
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