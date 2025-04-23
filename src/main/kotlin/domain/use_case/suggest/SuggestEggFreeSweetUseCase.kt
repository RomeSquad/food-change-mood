package domain.use_case.suggest

import data.meal.MealsRepository
import data.model.Meal

class SuggestEggFreeSweetUseCase(
    private val mealsRepository: MealsRepository
) {

    private val suggestEggFreeSweet: List<Meal> by lazy {
        mealsRepository.getAllMeals()
            .filter { meal ->
                !meal.ingredients.contains(EGG_INGREDIENT) &&
                        meal.tags.contains(SWEET_TAG)
            }
    }

    fun suggestRandomSweet(): Result<Meal> {
        return suggestEggFreeSweet.randomOrNull()?.let { Result.success(it) }
            ?: Result.failure(NoSuchElementException("No egg-free sweets available"))
    }

    private companion object {
        const val SWEET_TAG = "sweet"
        const val EGG_INGREDIENT = "egg"
    }
}