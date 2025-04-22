package domain.use_case

import model.Meal
import model.Nutrition
import java.util.*

fun createFakeMealDataForRandomEasyMeals (
    mealName : String,
    minutes : Int,
    nSteps : Int,
    nIngredients :Int
)=  Meal(
    name = mealName,
    minutes = minutes,
    nSteps = nSteps,
    nIngredients = nIngredients,
    contributorId = 47892,
    submitted = Date("9/16/2005"),
    tags = listOf(
        "30-minutes-or-less",
        "time-to-make",
        "course",
        "main-ingredient",
        "preparation",
        "italian",
        "easy"
    ),
    nutrition = Nutrition(
        calories = 450.0,
        totalFat = 18.0,
        sugar = 2.0,
        sodium = 650.0,
        protein = 22.0,
        saturatedFat = 8.0,
        carbohydrates = 45.0
    ),
    steps = listOf(
        "Bring large pot of salted water to boil",
        "Cook spaghetti according to package directions",
        "Whisk eggs and grated cheese in a bowl",
        "Cook pancetta until crispy, then add cooked pasta",
        "Quickly mix in egg mixture off heat to create creamy sauce"
    ),
    description = "Classic Roman pasta dish with eggs, cheese, pancetta, and pepper. The key is to mix the eggs quickly to create a creamy sauce without scrambling them.",
    ingredients = listOf(
        "spaghetti",
        "eggs",
        "pecorino cheese",
        "pancetta",
        "black pepper",
        "salt",
        "garlic"
    ),
    id = 137739
)
