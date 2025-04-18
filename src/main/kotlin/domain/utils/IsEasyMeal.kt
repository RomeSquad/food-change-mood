package domain.utils

import model.Meal

fun isEasyMeal(meal: Meal): Boolean =
    meal.minutes <= ConstNumberItem.THIRTY_ITEM && meal.nIngredients <= ConstNumberItem.FIVE_ITEM && meal.nSteps <= ConstNumberItem.SIX_ITEM
