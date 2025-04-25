package domain.use_case

import data.model.Meal
import data.model.Nutrition
import java.util.*


fun createFakeMealData(
    mealName: String? = null,
    id: Int? = null,
    minutes: Int? = null,
    contributorId: Int? = null,
    submitted: Date? = null,
    tags: List<String>? = null,
    nutrition: Nutrition? = null,
    stepsCount: Int? = null,
    steps: List<String>? = null,
    description: String? = null,
    ingredients: List<String>? = null,
    ingredientsCount: Int? = null
): Meal {

    return Meal(
        name = mealName ?: "Default Meal",
        id = id ?: 0,
        minutes = minutes ?: 30,
        contributorId = contributorId ?: 47892,
        submitted = submitted ?: Date(),
        tags = tags ?: listOf("default-tag"),
        nutrition = nutrition ?: Nutrition(
            calories = 500.0,
            totalFat = 20.0,
            sugar = 5.0,
            sodium = 800.0,
            protein = 25.0,
            saturatedFat = 10.0,
            carbohydrates = 50.0
        ),
        stepsCount = stepsCount ?: 3,
        steps = steps ?: listOf("Default step 1", "Default step 2", "Default step 3"),
        description = description ?: "Default description",
        ingredients = ingredients ?: listOf("Ingredient 1", "Ingredient 2"),
        ingredientsCount = ingredientsCount ?: 2
    )
}
