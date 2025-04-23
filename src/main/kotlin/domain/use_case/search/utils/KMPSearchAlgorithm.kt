package domain.use_case.search.utils

import model.Meal

class KMPSearchAlgorithm(
    private val patternMatcher: PatternMatcher
) : SearchAlgorithm {
    override fun search(data: List<Meal>, query: String): Result<List<Meal>> =
        query.takeIf { it.isNotBlank() }
            ?.let { nonBlankQuery ->
                data.filter { meal ->
                    patternMatcher.match(meal.name, nonBlankQuery)
                }.let { filteredMeals ->
                    if (filteredMeals.isNotEmpty()) Result.success(filteredMeals)
                    else Result.failure(Exception("Meal Not Found"))
                }
            } ?: Result.failure(Exception("Query must not be empty"))
}