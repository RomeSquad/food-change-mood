package domain.utils

import data.utils.ConstNumberMealsItem
import model.Meal

fun isEasyMeal(meal: Meal): Boolean =
    meal.minutes <= ConstNumberMealsItem.THIRTY_MEALS_ITEM
            && meal.nIngredients <= ConstNumberMealsItem.FIVE_MEALS_ITEM
            && meal.nSteps <= ConstNumberMealsItem.SIX_MEALS_ITEM
