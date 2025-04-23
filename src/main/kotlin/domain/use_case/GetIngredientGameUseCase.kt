package domain.use_case

import data.meal.MealsRepository
import model.Meal
import model.ingredient_game.Question

class GetIngredientGameUseCase(
    private val mealsRepository: MealsRepository
) {
    private var score = 0
     var correctCount = 0
    private val usedMeals = mutableSetOf<Meal>()

    fun getNextQuestion(): Question? {
        val meals = mealsRepository.getAllMeals().filter { it.ingredients.isNotEmpty() }
        val unusedMeals = meals.filterNot { it in usedMeals }
        if (unusedMeals.isEmpty() || correctCount >= 15) return null

        val meal = unusedMeals.random()
        usedMeals.add(meal)

        val correctIngredient = meal.ingredients.random()

        val wrongIngredients = meals
            .filter { it != meal && it.ingredients.isNotEmpty() }
            .flatMap { it.ingredients }
            .filter { it != correctIngredient }
            .shuffled()
            .take(2)

        if (wrongIngredients.size < 2) return null

        val options = (wrongIngredients + correctIngredient).shuffled()

        return Question(meal.name,options.toString(), correctIngredient )
    }

    fun submitAnswer(selected: String, correctAnswer :String): Boolean {
        if (selected == correctAnswer) {
            score += 1000
            correctCount++
            return true
        }
        return false
    }

    fun getScore() = score
}
fun Question.display(): String {
    return "Question(mealName=$mealName, options=$options)"
}


