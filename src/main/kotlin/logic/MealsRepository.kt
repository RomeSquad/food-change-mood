package logic

import model.Meal

interface MealsRepository {
    fun getAllMeals():Result<List<Meal>>
}