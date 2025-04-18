package logic.use_case


import data.meal.MealsRepository
import model.Meal

class GetKetoDietMealsUseCase(private val mealsRepository: MealsRepository) {

    fun getKetoMeals(): List<Meal> {
        return mealsRepository.getAllMeals().filter {
            it.nutrition.carbohydrates < TEN_ITEM &&
                    it.nutrition.totalFat >= FIFTEN_ITEM &&
                    it.nutrition.protein >= TEN_ITEM
        }.shuffled()
    }

    fun getNextKetoMeal(): Meal {
        val ketoMeals = getKetoMeals()
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


