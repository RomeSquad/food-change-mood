package logic.use_case
import model.Meal

class KetoDietHelper(private val meals: List<Meal>) {
    private val ketoMeals: List<Meal>
    private val usedKetoMeal: MutableList<Int>

    init {
        ketoMeals = getKetoMeals()
        usedKetoMeal = mutableListOf<Int>()

    }

    fun getKetoMeals(): List<Meal> {

        return meals.filter {
            it.nutrition.carbohydrates < 10 &&
                    it.nutrition.totalFat >= 15 &&
                    it.nutrition.protein >= 10
        }.shuffled()

    }

    fun getNextKetoMeal(): Meal {
        for (meal in ketoMeals) {
            if (!usedKetoMeal.contains(meal.id)) {
                usedKetoMeal.add(meal.id)
                return meal
            }
        }
        throw (Exception("There is no more keto meals left "))
    }

}


