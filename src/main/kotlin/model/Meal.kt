package model

import java.text.SimpleDateFormat
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
) {
    override fun toString(): String {
        return "Id=$id      Name='$name'\n" +
                "Time to prepare=$minutes \n" +
                "description=$description \n" +
                "$nutrition \n" +
                "consists of $nIngredients ingredients " +
                "ingredients: $ingredients  " +
                "How to prepare =$nSteps steps=$steps,\n" +
                "Shared by contributor $contributorId on ${submitted.date}-${submitted.month}-${submitted.year}\n" +
                "tags=$tags \n"

    }

    companion object{
        const val DATE_FORMAT = "dd-mm-yyyy"
    }
}
