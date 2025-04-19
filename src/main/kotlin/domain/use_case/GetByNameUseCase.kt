package domain.use_case

import data.meal.MealsRepository
import domain.search.SearchAlgorithm
import model.Meal

class GetByNameUseCase(
    private val repository: MealsRepository,
    private val searchAlgorithm: SearchAlgorithm
) {
     fun getByName(query: String): Result<List<Meal>>  {
         return searchAlgorithm.search(repository.getAllMeals(),query)
     }
}