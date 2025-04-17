package logic.use_case

import logic.MealsRepository
import model.Meal
import kotlin.math.absoluteValue

class GetMealsContainsCaloriesProteinUseCase(
    private val mealsRepository: MealsRepository,
) {
    fun getMealsContainCaloriesAndProtein(
        targetCalories: Double,
        targetProtein: Double,
        tolerance: Double = 0.02
    ): List<Meal> {

        validateUserInput(targetCalories, targetProtein)

        val allMeals = mealsRepository.getAllMeals()
        val highQualityMeals = allMeals.filter(::onlyHighQualityData)
        return highQualityMeals.findMatchesMealsBasedOn(targetCalories, targetProtein, tolerance)
            .takeIf { it.isNotEmpty() }
            ?: findClosestMealsBasedOn(highQualityMeals, targetCalories, targetProtein)
    }

    private fun onlyHighQualityData(meal: Meal): Boolean {
        return meal.nutrition.calories > 0 && meal.nutrition.protein > 0
    }

    private fun List<Meal>.findMatchesMealsBasedOn(targetCalories: Double, targetProtein: Double, tolerance: Double) =
        filter { meal ->
            isWithinTolerance(meal.nutrition.calories, targetCalories, tolerance) &&
                    isWithinTolerance(meal.nutrition.protein, targetProtein, tolerance)
        }

    private fun isWithinTolerance(mealValue: Double, targetValue: Double, tolerance: Double): Boolean {
        return mealValue in (targetValue * (1 - tolerance))..(targetValue * (1 + tolerance))
    }

    private fun findClosestMealsBasedOn(
        validMeals: List<Meal>,
        targetCalories: Double,
        targetProtein: Double
    ): List<Meal> {
        return validMeals
            .map { meal ->
                val distance = calculateMealDistance(meal, targetCalories, targetProtein)
                meal to distance
            }
            .sortedBy { it.second }
            .take(3)
            .map { it.first }
    }

    private fun calculateMealDistance(meal: Meal, targetCalories: Double, targetProtein: Double): Double {
        val calorieDiff = (meal.nutrition.calories - targetCalories).absoluteValue / targetCalories
        val proteinDiff = (meal.nutrition.protein - targetProtein).absoluteValue / targetProtein
        return calorieDiff + proteinDiff
    }

    private fun validateUserInput(targetCalories: Double, targetProtein: Double) {
        if (targetCalories <= 0 || targetProtein <= 0) {
            throw IllegalArgumentException("Calories and protein values must be positive.")
        }
    }
}