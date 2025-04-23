package domain.use_case.game

import data.meal.MealsRepository
import domain.use_case.game.IngredientGameUseCase.Constant.MAX_CORRECT_ANSWERS
import domain.use_case.game.IngredientGameUseCase.Constant.MAX_WRONG_OPTIONS
import domain.use_case.game.IngredientGameUseCase.Constant.MIN_WRONG_OPTIONS
import domain.use_case.game.IngredientGameUseCase.Constant.POINTS_PER_CORRECT_ANSWER
import model.Meal
import model.ingredient_game.Question

class IngredientGameUseCase(
    private val mealsRepository: MealsRepository
) {
    private var score: Int = 0
    private var correctCount: Int = 0
    private val usedMeals = mutableSetOf<Meal>()
    private val allMeals: List<Meal> by lazy { mealsRepository.getAllMeals().filter { it.ingredients.isNotEmpty() } }

    fun getNextQuestion(): Question? {
        if (correctCount >= MAX_CORRECT_ANSWERS || allMeals.isEmpty()) return null

        val unusedMeals = allMeals.filterNot { it in usedMeals }
        if (unusedMeals.isEmpty()) return null

        val meal = unusedMeals.random().also { usedMeals.add(it) }
        val correctIngredient = meal.ingredients.random()

        val wrongIngredients = getWrongIngredients(meal, correctIngredient)
        if (wrongIngredients.size < MIN_WRONG_OPTIONS) return null

        val options = (wrongIngredients + correctIngredient).shuffled()

        return Question(
            mealName = meal.name,
            options = options.toString(),
            correctAnswer = correctIngredient
        )
    }

    fun submitAnswer(selectedAnswer: String, correctAnswer: String): Boolean {
        val isCorrect = selectedAnswer == correctAnswer
        if (isCorrect) {
            score += POINTS_PER_CORRECT_ANSWER
            correctCount++
        }
        return isCorrect
    }

    fun getScore(): Int = score

    private fun getWrongIngredients(currentMeal: Meal, correctIngredient: String): List<String> {
        return allMeals
            .filter { it != currentMeal }
            .flatMap { it.ingredients }
            .filter { it != correctIngredient }
            .shuffled()
            .take(MAX_WRONG_OPTIONS)
    }

    private object Constant {
        const val MAX_CORRECT_ANSWERS = 15
        const val POINTS_PER_CORRECT_ANSWER = 1000
        const val MIN_WRONG_OPTIONS = 2
        const val MAX_WRONG_OPTIONS = 2
    }
}



