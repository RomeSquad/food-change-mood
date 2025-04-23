package domain.use_case

import data.meal.MealsRepository
import data.utils.NoMealsFoundException
import model.Meal
import java.text.SimpleDateFormat

class GetByDateUseCase(
    private val mealsRepository: MealsRepository,
) {

    fun getByDate(date: String): List<Meal> {
        validateDate(date)

        val dateParsed = SimpleDateFormat(Meal.DATE_FORMAT).parse(date)
        val meals = mealsRepository.getAllMeals()

        return meals.filter { it.submitted == dateParsed }
            .takeIf { it.isNotEmpty() } ?: throw NoMealsFoundException("No meals found for date: $date")
    }

    private fun validateDate(date: String) {
        if (date.isEmpty())
            throw IllegalArgumentException("Date cannot be empty")

        if (!date.trim().matches(Meal.DATE_REGEX))
            throw IllegalArgumentException("Invalid date format: $date. Expected format: ${Meal.DATE_FORMAT}")

    }
}