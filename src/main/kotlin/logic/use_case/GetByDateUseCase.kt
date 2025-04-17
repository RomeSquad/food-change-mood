package logic.use_case

import logic.MealsRepository
import model.Meal
import java.text.ParseException
import java.text.SimpleDateFormat

class GetByDateUseCase(
    private val mealsRepository: MealsRepository,
) {
    fun getByDate(date: String): Result<List<Meal>> {
        try {
            val dateParsed = SimpleDateFormat(Meal.DATE_FORMAT).parse(date)
            val meals = mealsRepository.getAllMeals()
            val filtered = meals.filter { it.submitted == dateParsed }
            return if (filtered.isEmpty())
                Result.failure(Exception("No meals found for date: $date"))
            else
                Result.success(filtered)
        } catch (e: ParseException) {
            return Result.failure(Exception("Invalid date format: $date. Expected format: ${Meal.DATE_FORMAT}"))
        } catch (e: Exception) {
            return Result.failure(Exception("An error happened: ${e.message}"))
        }
    }
}