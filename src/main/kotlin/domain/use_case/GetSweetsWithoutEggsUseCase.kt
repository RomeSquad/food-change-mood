package domain.use_case

import data.meal.MealsRepository
import model.Meal

class GetSweetsWithoutEggsUseCase(
    private val mealsRepository: MealsRepository
) {

    private var sweetsWithoutEggs:MutableList<Meal> = getAllSweetsWithoutEggs() as MutableList

    private fun getAllSweetsWithoutEggs(): List<Meal> {
        return mealsRepository
            .getAllMeals()
            .filter {
                !it.ingredients.contains(EGG) && it.tags.contains(SWEET)
            }
    }


    fun getRandomSweet(): Result<Meal> {
        val sweetsListIndices =  sweetsWithoutEggs.indices
        return if(sweetsListIndices.isEmpty()){
            Result.failure(Exception("Not found sweets without eggs"))
        }else{
            val randomIndex = sweetsListIndices.random()
            removeSweetByIndex(randomIndex)
            return Result.success(sweetsWithoutEggs[randomIndex])
        }
    }

    private fun removeSweetByIndex(index :Int ){
        sweetsWithoutEggs.removeAt(index)
    }


    companion object{
        const val EGG = "egg"
        const val SWEET = "sweet"
    }
}