package logic.use_case

import logic.MealsRepository
import logic.utils.SearchAlgorithm
import model.Meal

class SearchByNameUseCase(
    private val repository: MealsRepository,
    private val searchAlgorithm: SearchAlgorithm
) {
     fun searchByName(query: String): Result<List<Meal>>  = searchAlgorithm.search(repository.getAllMeals(),query)
}