package logic

import model.Meal
import kotlin.math.absoluteValue

class GetMealsHaveProteinCaloriesUseCase(
    private val mealsRepository: MealsRepository,
) {
    fun getMealsHaveProteinAndCalories(calories: Double, protein: Double, tolerance: Double = 0.2): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter(::onlyHighQualityData)
            .findMatches(calories, protein, tolerance)
            .ifEmpty { findClosestMatches(calories, protein) }
    }

    private fun onlyHighQualityData(meal: Meal): Boolean {
        return meal.nutrition.calories > 0 && meal.nutrition.protein > 0
    }

    private fun List<Meal>.findMatches(targetCal: Double, targetPro: Double, tolerance: Double) =
        filter { meal ->
            val (cal, pro) = meal.nutrition
            cal in (targetCal * (1 - tolerance))..(targetCal * (1 + tolerance)) &&
                    pro in (targetPro * (1 - tolerance))..(targetPro * (1 + tolerance))
        }

    private fun findClosestMatches(targetCal: Double, targetPro: Double) =
        mealsRepository.getAllMeals()
            .sortedBy { meal ->
                val (cal, pro) = meal.nutrition
                (cal - targetCal).absoluteValue / targetCal +
                        (pro - targetPro).absoluteValue / targetPro
            }
            .take(3)
}

