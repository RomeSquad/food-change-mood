package domain.use_case

import data.meal.MealsRepository
import model.Meal
import java.text.SimpleDateFormat

class GetByDateUseCase(
    private val mealsRepository: MealsRepository,
) {

    fun getByDate(date: String): List<Meal> {
        validateDate(date)

        val dateParsed = SimpleDateFormat(Meal.DATE_FORMAT).parse(date)
        val meals = mealsRepository.getAllMeals()

        val filtered = meals.filter { it.submitted == dateParsed }
        if (filtered.isEmpty())
            throw Exception("No meals found for date: $date")
        else
            return filtered
    }

    fun validateDate(date: String) {
        if (date.isEmpty())
            throw Exception("Date cannot be empty")

        if (!date.trim().matches(Meal.DATE_REGEX))
            throw Exception("Invalid date format: $date. Expected format: ${Meal.DATE_FORMAT}")

    }
}