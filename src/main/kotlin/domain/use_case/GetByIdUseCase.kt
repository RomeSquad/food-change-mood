package domain.use_case

import model.Meal

class GetByIdUseCase {

    fun getById(id: String, meals: List<Meal>): Result<Meal> {
        val trimmedId = id.trim()
        return if (trimmedId.isEmpty())
            Result.failure(Exception("ID cannot be empty"))
        else if (!trimmedId.all { it.isDigit() })
            Result.failure(Exception("$id is not valid. ID consists of digits only."))
        else {
            val meal = meals.find { it.id == trimmedId.toInt() }
            if (meal != null) {
                Result.success(meal)
            } else {
                Result.failure(Exception("No meal found with id: $trimmedId"))
            }
        }
    }


}