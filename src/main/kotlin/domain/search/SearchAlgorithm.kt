package domain.search

import model.Meal

interface SearchAlgorithm {
    fun search(data:List<Meal>,query: String):Result<List<Meal>>
}