package logic.use_case

import logic.MealsRepository
import model.Meal
import java.util.*

class GetById(
    private val mealsRepository: MealsRepository,
    private val id: Int
) {
    fun getByDate(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { it.id == id }
    }
}