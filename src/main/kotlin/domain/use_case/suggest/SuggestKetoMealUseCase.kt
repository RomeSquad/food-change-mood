package domain.use_case.suggest

import data.meal.MealsRepository
import domain.NoMealsFoundException
import data.model.Meal

class SuggestKetoMealUseCase(private val mealsRepository: MealsRepository) {

    fun suggestKetoMeal(): List<Meal> {
        return mealsRepository.getAllMeals().filter {
            it.nutrition.carbohydrates < TEN_ITEM &&
                    it.nutrition.totalFat >= FIFTEN_ITEM &&
                    it.nutrition.protein >= TEN_ITEM
        }.takeIf { it.isNotEmpty() }
            ?.shuffled()
            ?: throw NoMealsFoundException("No keto‑friendly meals found")
    }

    fun getNextKetoMeal(): Meal {
        val ketoMeals = suggestKetoMeal()
        val usedKetoMeal = mutableListOf<Int>()

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
        private const val FIFTEN_ITEM = 15
    }
}