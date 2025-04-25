package domain.use_case.fetch

import data.meal.MealsRepository
import data.model.Meal

class GetQuickHealthyMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getQuickHealthyMeals(): List<Meal> {
        val meals = mealsRepository.getAllMeals()

        if (meals.isEmpty()) return emptyList()

        val nutritionValues = meals.map { it.nutrition }
        val fatThreshold = quartile(nutritionValues.map { it.totalFat })
        val saturatedThreshold = quartile(nutritionValues.map { it.saturatedFat })
        val carbsThreshold = quartile(nutritionValues.map { it.carbohydrates })

        return meals.filter { meal ->
            meal.minutes <= 15 &&
                    meal.nutrition.totalFat <= fatThreshold &&
                    meal.nutrition.saturatedFat <= saturatedThreshold &&
                    meal.nutrition.carbohydrates <= carbsThreshold
        }
    }

    private fun quartile(values: List<Double>): Double {
        val sortedValues = values.sorted()
        return when {
            sortedValues.isEmpty() -> 0.0
            values.size % 4 == 0 -> sortedValues[values.size / 4 - 1]
            else -> sortedValues[values.size / 4]
        }
    }
}