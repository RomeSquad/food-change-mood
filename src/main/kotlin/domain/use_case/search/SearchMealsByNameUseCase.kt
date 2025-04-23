package domain.use_case.search

import data.meal.MealsRepository
import domain.use_case.search.utils.SearchAlgorithm
import model.Meal

class SearchMealsByNameUseCase(
    private val repository: MealsRepository,
    private val searchAlgorithm: SearchAlgorithm
) {
     fun searchMealsByName(query: String): Result<List<Meal>>  {
         return searchAlgorithm.search(repository.getAllMeals(),query)
     }
}