package logic.utils

import model.Meal
import javax.management.Query

interface SearchAlgorithm {
    fun search(data:List<Meal>,query: String):Result<List<Meal>>
}