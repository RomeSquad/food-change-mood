package domain.use_case.search.utils

import model.Meal

interface SearchAlgorithm {
    fun search(data:List<Meal>,query: String):Result<List<Meal>>
}