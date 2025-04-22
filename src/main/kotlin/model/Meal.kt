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
        val builder = StringBuilder()

        builder.appendLine("=".repeat(50))
        builder.appendLine(String.format("%-20s: %s", "Meal", name))
        builder.appendLine(String.format("%-20s: %s", "Description", description ?: "No description"))
        builder.appendLine(String.format("%-20s: %s", "Preparation Time", "$minutes minutes"))
        builder.appendLine(String.format("%-20s: %s", "Ingredients", "$nIngredients items"))
        builder.appendLine(String.format("%-20s: %s", "Steps", "$nSteps steps"))
        builder.appendLine(String.format("%-20s: %s", "Tags", tags.joinToString(", ")))
        builder.appendLine("-".repeat(50))

        builder.appendLine("Ingredients List:")
        val columns = 4
        ingredients.chunked(columns).forEach { row ->
            val formattedRow = row.joinToString(" ") { ingredient ->
                String.format("%-30s", "- $ingredient")
            }
            builder.appendLine(formattedRow)
        }

        builder.appendLine("\nPreparation Steps:")
        steps.withIndex().forEach { (i, step) ->
            builder.appendLine("  ${i + 1}. $step")
        }

        builder.appendLine("\n$nutrition")

        builder.appendLine("=".repeat(50))
        return builder.toString()
    }


    companion object {
        const val DATE_FORMAT = "yyyy-mm-dd"
        val DATE_REGEX = Regex("\\d{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[12]\\d|3[01])")
    }
}
