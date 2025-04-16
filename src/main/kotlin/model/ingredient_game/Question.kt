package model.ingredient_game

data class Question(
    val mealName: String,
    val options: List<String>,
    val correctAnswer: String
)
