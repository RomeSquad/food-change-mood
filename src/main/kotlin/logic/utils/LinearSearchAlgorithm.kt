package logic.utils

import model.Meal

class LinearSearchAlgorithm : SearchAlgorithm {
    override fun search(data: List<Meal>, query: String): Result<List<Meal>> {
        if (query.isBlank()) return Result.failure(Exception("Query must not be empty."))
        val lowerCaseQuery = query.lowercase()

        val results = data.filter { meal ->
            meal.name.lowercase().contains(lowerCaseQuery, ignoreCase = true)
        }

        return if (results.isNotEmpty()) {
            Result.success(results)
        } else {
            Result.failure(Exception("Meals Not Found."))
        }
    }
}