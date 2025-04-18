package logic.use_case

import logic.MealsRepository
import model.Meal

class GetSweetsWithNoEggsUseCase(
    private val mealsRepository: MealsRepository
) {
    private var sweetsWithNoEggsList:MutableList<Meal> = getAllSweetsWithNoEggs() as MutableList

    private fun getAllSweetsWithNoEggs(): List<Meal> {
        return mealsRepository
            .getAllMeals()
            .filter {
                !it.ingredients.contains("egg") && it.tags.contains("sweet")
            }
    }

    fun getRandomSweetWithNoEggs():  Meal {
        val randomIndex =  sweetsWithNoEggsList.indices.random()
        removeSweetWithNoEggsItemWithIndex(randomIndex)
        return sweetsWithNoEggsList[randomIndex]
    }

    private fun removeSweetWithNoEggsItemWithIndex(index :Int ){
        sweetsWithNoEggsList.removeAt(index)
    }




}