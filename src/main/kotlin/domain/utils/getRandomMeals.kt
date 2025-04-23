package domain.utils

import model.Meal

fun List<Meal>.getRandomMeals(count: Int): List<Meal> {
    return this.filter(::isEasyMeal)
        .shuffled()
        .take(count)

}