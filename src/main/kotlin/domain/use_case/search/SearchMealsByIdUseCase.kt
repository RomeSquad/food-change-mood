package domain.use_case.search

import domain.NoMealsFoundException
import data.model.Meal

class SearchMealsByIdUseCase {

    fun getById(id: String, meals: List<Meal>): Meal {
        val trimmedId = id.trim()
        if (trimmedId.isEmpty())
            throw IllegalArgumentException("ID cannot be empty")
        else if (!trimmedId.all { it.isDigit() })
            throw IllegalArgumentException("$id is not valid. ID consists of digits only.")

        return meals.find { it.id == trimmedId.toInt() } ?: throw NoMealsFoundException("$id is not found")

    }


}