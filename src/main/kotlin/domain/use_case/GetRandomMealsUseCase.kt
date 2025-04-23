package domain.use_case

import data.meal.MealsRepository
import data.utils.ConstNumberMealsItem.TEN_MEALS_ITEM
import domain.utils.getRandomMeals
import model.Meal

class GetRandomMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getNRandomEasyMeals(count: Int = TEN_MEALS_ITEM): List<Meal> = mealsRepository.getAllMeals().getRandomMeals(count)

}