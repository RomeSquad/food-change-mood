package domain.use_case

import data.meal.MealsRepository
import model.Meal
import java.text.SimpleDateFormat

class GetByDateUseCase(
    private val mealsRepository: MealsRepository,
) {
    fun getByDate(date: String): Result<List<Meal>> {
        if (date.isEmpty())
            return Result.failure(Exception("Date cannot be empty"))
        if (!date.trim().matches(Regex("(0?[1-9]|[1-2][0-9]|3[01])-(0?[1-9]|1[0-2])-[1-2]\\d{3}")))
            return Result.failure(Exception("Invalid date format: $date. Expected format: ${Meal.DATE_FORMAT}"))

        val dateParsed = SimpleDateFormat(Meal.DATE_FORMAT).parse(date)
        val meals = mealsRepository.getAllMeals()
        val filtered = meals.filter { it.submitted == dateParsed }
        return if (filtered.isEmpty())
            Result.failure(Exception("No meals found for date: $date"))
        else
            Result.success(filtered)
    }
}