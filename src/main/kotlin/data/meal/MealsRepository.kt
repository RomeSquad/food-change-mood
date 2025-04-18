package data.meal

import model.Meal

interface MealsRepository {
    fun getAllMeals():List<Meal>
}