package domain.use_case

import data.model.Meal
import data.model.Nutrition
import java.util.*


fun createFakeMealData(
    mealName: String = "Default Meal",
    id: Int = 0,
    minutes: Int = 30,
    contributorId: Int = 47892,
    submitted: Date = Date(),
    tags: List<String> = listOf("default-tag"),
    nutrition: Nutrition = createFakeNutrition(),
    stepsCount: Int = 3,
    steps: List<String> = listOf("Default step 1", "Default step 2", "Default step 3"),
    description: String = "Default description",
    ingredients: List<String> = listOf("Ingredient 1", "Ingredient 2"),
    ingredientsCount: Int = 2
): Meal {

    return Meal(
        name = mealName,
        id = id,
        minutes = minutes,
        contributorId = contributorId,
        submitted = submitted,
        tags = tags,
        nutrition = nutrition,
        stepsCount = stepsCount,
        steps = steps,
        description = description,
        ingredients = ingredients,
        ingredientsCount = ingredientsCount
    )
}

fun createFakeNutrition(
    calories: Double = 500.0,
    totalFat: Double = 20.0,
    sugar: Double = 5.0,
    sodium: Double = 800.0,
    protein: Double = 25.0,
    saturatedFat: Double = 10.0,
    carbohydrates: Double = 50.0
): Nutrition {
    return Nutrition(
        calories = calories,
        totalFat = totalFat,
        sugar = sugar,
        sodium = sodium,
        protein = protein,
        saturatedFat = saturatedFat,
        carbohydrates = carbohydrates
    )
}


