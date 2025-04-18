package domain.use_case

import data.meal.MealsRepository
import domain.utils.ConstNumberItem.TEN_ITEM
import domain.utils.getRandomEasyMeals
import model.Meal

class GetRandomMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getTenRandomEasyMeals(): List<Meal> {
        return mealsRepository.getAllMeals().getRandomEasyMeals(TEN_ITEM)

    }

    fun getNRandomEasyMeals(count: Int): List<Meal> {
        return mealsRepository.getAllMeals().getRandomEasyMeals(count)

    }
}