package model

import java.util.Date

data class Meal(
    val name: String,
    val id: Int,
    val minutes: Int,
    val contributorId: Int,
    val submitted: Date,
    val tags: List<String>,
    val nutrition: Nutrition,
    val nSteps: Int,
    val steps: List<String>,
    val description: String?,
    val ingredients: List<String>,
    val nIngredients: Int,
    val addDate: Date
)
// mohaemd ,  5 ,  5  , 5  , 25/11/1970  ,  [1,2,3]  , 1,2,3,4,5,6,7,  5  , [1,2,3] , moha?,[1,2,3], 5, 25/11/2001