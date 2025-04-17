package logic.use_case

import logic.MealsRepository
import java.text.ParseException
import java.text.SimpleDateFormat

class GetByDate(
    private val mealsRepository: MealsRepository,
) {
    fun getByDate(date: String): Result<List<Pair<Int, String>>> {
        try {
            val dateParsed = SimpleDateFormat("dd-mm-yyyy").parse(date)
            val meals = mealsRepository.getAllMeals()
            val filtered = meals.filter { it.submitted == dateParsed }
            return if (filtered.isEmpty())
                Result.failure(Exception("No meals found for date: $date"))
            else
                Result.success(filtered.map { it.let { Pair(it.id, it.name) } })
        } catch (e: ParseException) {
            return Result.failure(Exception("Invalid date format: $date. Expected format: dd-mm-yyyy"))
        } catch (e: Exception) {
            return Result.failure(Exception("An error happened: ${e.message}"))
        }
    }
}