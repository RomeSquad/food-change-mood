package domain.use_case.search

import data.meal.MealsRepository
import model.Meal
import model.gym_helper.GymHelperInput
import kotlin.math.absoluteValue

class SearchGymFriendlyMealsUseCase(
    private val mealsRepository: MealsRepository,
) {
    fun getMealsByCaloriesAndProtein(
        input: GymHelperInput
    ): List<Meal> {

        validateUserInput(
            input.caloriesAndProteinTolerance.caloriesTolerance,
            input.caloriesAndProteinTolerance.proteinTolerance
        )

        val allMeals = mealsRepository.getAllMeals()
        val matchesMeals = allMeals.findMatchesMealsBasedOnCaloriesAndProtein(input)
        return if (matchesMeals.isNotEmpty()) {
            matchesMeals
        } else {
            throw NoSuchElementException(
                "No meals found matching calories = ${input.calories} ± ${input.caloriesAndProteinTolerance.caloriesTolerance} " +
                        "and protein = ${input.protein} ± ${input.caloriesAndProteinTolerance.proteinTolerance}"
            )
        }
    }

    private fun List<Meal>.findMatchesMealsBasedOnCaloriesAndProtein(input: GymHelperInput): List<Meal> =
        this.filter { meal ->
            val calories = meal.nutrition.calories
            val protein = meal.nutrition.protein

            val calorieDifference = (calories - input.calories).absoluteValue
            val proteinDifference = (protein - input.protein).absoluteValue

            calorieDifference <= input.caloriesAndProteinTolerance.caloriesTolerance && proteinDifference <= input.caloriesAndProteinTolerance.proteinTolerance
        }

    private fun validateUserInput(caloriesTolerance: Int, proteinTolerance: Int) {
        if (caloriesTolerance < 0) {
            throw IllegalArgumentException("Calories tolerance must be a non-negative integer.")
        }
        if (proteinTolerance < 0) {
            throw IllegalArgumentException("Protein tolerance must be a non-negative integer.")
        }
    }
}