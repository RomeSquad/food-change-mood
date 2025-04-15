package logic

import model.Meal

class IdentifyIraqiMealsUseCase (
    private val mealsRepository: MealsRepository
) {
    fun identifyIraqiMeals(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter {
                it.tags.toString().contains("iraqi") ||
                it.description?.contains("Iraq", true) ?: false
            }
    }
}