package domain.use_case

import data.model.Meal
import data.model.Nutrition
import java.util.Date

fun createMeal(
    tag: List<String>,
    description: String?
): Meal {
    return Meal(
        name = "",
        id = 0,
        minutes = 0,
        contributorId = 0,
        submitted = Date(),
        tags = tag,
        nutrition = Nutrition(
            calories = 0.0,
            totalFat = 0.0,
            sugar = 0.0,
            sodium = 0.0,
            protein = 0.0,
            saturatedFat = 0.0,
            carbohydrates = 0.0
        ),
        stepsCount = 0,
        steps = listOf("",""),
        description = description,
        ingredients = listOf("",""),
        ingredientsCount = 0
    )
}