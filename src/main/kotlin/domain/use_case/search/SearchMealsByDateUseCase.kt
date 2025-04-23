package domain.use_case.search

import data.meal.MealsRepository
import domain.NoMealsFoundException
import domain.use_case.search.utils.DateValidator
import data.model.Meal
import java.text.SimpleDateFormat

class SearchMealsByDateUseCase(
    private val mealsRepository: MealsRepository,
    private val dateValidator: DateValidator = DateValidator()
) {
    fun searchMealsByDate(date: String): List<Meal> {
        dateValidator.validate(date)

        return mealsRepository.getAllMeals()
            .filter { meal -> meal.matchesDate(date) }
            .ifEmpty { throw NoMealsFoundException("No meals found for date: $date") }
    }

    private fun Meal.matchesDate(date: String): Boolean {
        val dateParsed = SimpleDateFormat(Meal.DATE_FORMAT).parse(date)
        return submitted == dateParsed
    }
}