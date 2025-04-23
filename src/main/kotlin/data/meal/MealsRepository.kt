package data.meal

import data.model.Meal

interface MealsRepository {
    fun getAllMeals(): List<Meal>
}