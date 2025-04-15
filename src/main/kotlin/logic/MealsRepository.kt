package logic

import model.Meal

interface MealsRepository {
    fun getAllMeals():List<Meal>
}