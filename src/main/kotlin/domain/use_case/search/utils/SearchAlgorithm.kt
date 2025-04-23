package domain.use_case.search.utils

import data.model.Meal

interface SearchAlgorithm {
    fun search(data:List<Meal>,query: String):Result<List<Meal>>
}