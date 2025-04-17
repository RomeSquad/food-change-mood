package logic.use_case

import model.Meal

class ketoDietHelper() {

    private val meals: List<Meal> = listOf()
    private val ketoMeals = getKetoMeals()
    private val usedKetoMeal: MutableList<Int> = mutableListOf<Int>()

    private fun getKetoMeals(): List<Meal> {

        return meals.filter {
            it.nutrition.carbohydrates < 10 &&
                    it.nutrition.totalFat >= 15 &&
                    it.nutrition.protein >= 10
        }.shuffled()

    }

    fun getNextKetoMeal() {
        for (meal in ketoMeals) {

        }
    }

}


