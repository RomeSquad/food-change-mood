package domain.use_case

import data.meal.MealsRepository
import model.Meal
import java.text.SimpleDateFormat

class GetByDateUseCase(
    private val mealsRepository: MealsRepository,
) {

    fun getByDate(date: String): List<Meal> {
        if (date.isEmpty())
            throw Exception("Date cannot be empty")

        if (!date.trim().matches(Regex("\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])")))
            throw Exception("Invalid date format: $date. Expected format: ${Meal.DATE_FORMAT}")

        val dateParsed = SimpleDateFormat(Meal.DATE_FORMAT).parse(date)
        val meals = mealsRepository.getAllMeals()

        val filtered = meals.filter { it.submitted == dateParsed }
        if (filtered.isEmpty())
            throw Exception("No meals found for date: $date")
        else
            return filtered
    }
}