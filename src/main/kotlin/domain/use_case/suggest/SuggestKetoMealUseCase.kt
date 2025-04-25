package domain.use_case.suggest

import data.meal.MealsRepository
import domain.NoMealsFoundException
import data.model.Meal

class SuggestKetoMealUseCase(private val mealsRepository: MealsRepository) {
    private val usedKetoMeal = mutableListOf<Int>()

    private fun suggestKetoMeal(): List<Meal> {
        val meals = mealsRepository.getAllMeals().filter {
            it.nutrition.carbohydrates < TEN_ITEM &&
                    it.nutrition.totalFat >= FIFTEEN_ITEM &&
                    it.nutrition.protein >= TEN_ITEM
        }.shuffled()
        if (meals.isEmpty()) {
            throw NoMealsFoundException("No keto‑friendly meals found")
        } else return meals
    }

    fun getNextKetoMeal(): Meal {
        val ketoMeals = suggestKetoMeal()

        for (meal in ketoMeals) {
            if (!usedKetoMeal.contains(meal.id)) {
                usedKetoMeal.add(meal.id)
                return meal
            }
        }
        throw (Exception("There is no more keto meals left "))
    }

    companion object {
        private const val TEN_ITEM = 10
        private const val FIFTEEN_ITEM = 15
    }
}