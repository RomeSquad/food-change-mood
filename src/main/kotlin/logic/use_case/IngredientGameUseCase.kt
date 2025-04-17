package logic.use_case

import logic.MealsRepository
import model.Meal
import model.ingredient_game.Question

class IngredientGameUseCase(
    private val mealsRepository: MealsRepository
) {
    private var score = 0
    private var correctCount = 0
    private val usedMeals = mutableSetOf<Meal>()

    fun getNextQuestion(): Question? {
        val meals = mealsRepository.getAllMeals().filter { it.ingredients.size >= 1 }
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

        return Question(meal.name, correctIngredient, options.toString())
    }

    fun submitAnswer(selected: String, correct: String): Boolean {
        if (selected == correct) {
            score += 1000
            correctCount++
            return true
        }
        return false
    }

    fun getScore() = score
}


