package domain.use_case

import data.meal.MealsRepository
import data.utils.ConstNumberMealsItem.TEN_MEALS_ITEM
import domain.utils.getRandomEasyMeals
import model.Meal

class GetRandomMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getTenRandomEasyMeals(): List<Meal> {
        return mealsRepository.getAllMeals().getRandomEasyMeals(TEN_MEALS_ITEM)

    }

    fun getNRandomEasyMeals(count: Int): List<Meal> {
        return mealsRepository.getAllMeals().getRandomEasyMeals(count)

    }
}