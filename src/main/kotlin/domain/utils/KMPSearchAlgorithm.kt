package domain.utils

import model.Meal

class KMPSearchAlgorithm(
    private val patternMatcher: PatternMatcher = KMPPatternMatcher(),
):SearchAlgorithm{
    override fun search(data: List<Meal>, query: String): Result<List<Meal>> {
        if(query.isBlank()) return Result.failure(Exception("Query must not be empty"))
        val result = data.filter { meal->
            patternMatcher.match(meal.name,query)
        }
        return if (result.isNotEmpty()){
            Result.success(result)
        }else{
            Result.failure(Exception("Meal Not Found"))
        }
    }
}
