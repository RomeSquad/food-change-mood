package domain.use_case

import data.meal.MealsRepository
import domain.search.SearchAlgorithm
import domain.search.SearchAlgorithmFactory
import model.Meal

class GetByNameUseCase(
    private val repository: MealsRepository,
    private val searchAlgorithmFactory: SearchAlgorithmFactory
) {
     fun getByName(query: String,searchType: SearchAlgorithmFactory.SearchType = SearchAlgorithmFactory.SearchType.KMPSearchAlgorithm): Result<List<Meal>>  {
         val searchAlgorithm = searchAlgorithmFactory.createSearchAlgorithm(searchType)
         return searchAlgorithm.search(repository.getAllMeals(),query)
     }
}