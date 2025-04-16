package logic.use_case

import logic.MealsRepository
import model.Meal
import java.util.Date

class GetByDate(
    private val mealsRepository: MealsRepository,
    private val date: Date
) {
    fun getByDate(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { it.submitted == date }
    }
}