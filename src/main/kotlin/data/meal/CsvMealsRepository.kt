package data.meal

import data.utils.CsvFileReader
import data.utils.CsvParser
import data.utils.MealColumnIndex
import data.utils.NutritionColumnIndex
import model.Meal
import model.Nutrition
import java.text.SimpleDateFormat
import java.util.*

class CsvMealsRepository(
    private val csvFileReader: CsvFileReader,
    private val csvParser: CsvParser
) : MealsRepository {

    private val dateFormat = SimpleDateFormat(Meal.DATE_FORMAT)
    private val meals by lazy { loadMeals() }
    override fun getAllMeals() = meals


    private fun loadMeals(): List<Meal> {
        val csvLines = csvFileReader.readLines()
        val parsedRows = csvParser.parseCsv(csvLines.joinToString("\n"))
        return parseRecords(parsedRows)
    }

    private fun parseRecords(rows: List<List<String>>): List<Meal> {
        return rows.drop(1)
            .filter(::isValidRow)
            .mapIndexedNotNull { index, row -> safelyMapRow(index, row) }
    }

    private fun safelyMapRow(index: Int, row: List<String>): Meal? {
        return try {
            mapRowToMeal(row)
        } catch (e: Exception) {
            println("Error parsing row ${index + 1}: ${e.message}")
            null
        }
    }

    private fun isValidRow(row: List<String>): Boolean {
        return row.size >= 12
    }

    private fun mapRowToMeal(
        row: List<String>,
    ): Meal {
        val nutritionValues = csvParser.parseDoubleList(row[MealColumnIndex.NUTRITION])

        validateNutritionValues(row, nutritionValues)
        return Meal(
            name = constructName(row),
            id = constructId(row),
            minutes = constructMinutes(row),
            contributorId = constructContributorId(row),
            submitted = constructSubmittedDate(row),
            tags = constructTags(row),
            nutrition = createNutritionFromValues(row),
            nSteps = constructNSteps(row),
            steps = constructSteps(row),
            description = constructDescription(row),
            ingredients = constructIngredients(row),
            nIngredients = constructNIngredients(row),
        )
    }

    private fun validateNutritionValues(row: List<String>, nutritionValues: List<Double>) {
        if (nutritionValues.size < 7) {
            throw IllegalArgumentException("Invalid nutrition data: ${row[MealColumnIndex.NUTRITION]}")
        }
    }

    private fun constructNIngredients(row: List<String>) = (row[MealColumnIndex.N_INGREDIENTS].toIntOrNull()
        ?: throw IllegalArgumentException("Invalid n_ingredients"))

    private fun constructIngredients(row: List<String>) =
        csvParser.parseStringList(row[MealColumnIndex.INGREDIENTS])

    private fun constructDescription(row: List<String>): String? {
        return row[MealColumnIndex.DESCRIPTION].trim().removeSurrounding("\"", "\"").takeIf { it.isNotEmpty() }
    }

    private fun constructSteps(row: List<String>) =
        csvParser.parseStringList(row[MealColumnIndex.STEPS])

    private fun constructNSteps(row: List<String>) =
        row[MealColumnIndex.N_STEPS].toIntOrNull() ?: throw IllegalArgumentException("Invalid N Steps")

    private fun constructTags(row: List<String>) = csvParser.parseStringList(row[MealColumnIndex.TAGS])

    private fun constructSubmittedDate(row: List<String>): Date =
        dateFormat.parse(row[MealColumnIndex.SUBMITTED])

    private fun constructContributorId(row: List<String>) = (row[MealColumnIndex.CONTRIBUTOR_ID].toIntOrNull()
        ?: throw IllegalArgumentException("Invalid Contributor ID"))

    private fun constructMinutes(row: List<String>) = (row[MealColumnIndex.MINUTES].toIntOrNull()
        ?: throw IllegalArgumentException("Invalid Minutes Number"))

    private fun constructId(row: List<String>) =
        row[MealColumnIndex.ID].toIntOrNull() ?: throw IllegalArgumentException("Invalid Meal ID")

    private fun constructName(row: List<String>) = row[MealColumnIndex.NAME].trim()

    private fun createNutritionFromValues(row: List<String>): Nutrition {
        val nutritionValues = csvParser.parseDoubleList(row[MealColumnIndex.NUTRITION])
        return Nutrition(
            calories = nutritionValues[NutritionColumnIndex.CALORIES],
            totalFat = nutritionValues[NutritionColumnIndex.TOTAL_FAT],
            sugar = nutritionValues[NutritionColumnIndex.SUGAR],
            sodium = nutritionValues[NutritionColumnIndex.SODIUM],
            protein = nutritionValues[NutritionColumnIndex.PROTEIN],
            saturatedFat = nutritionValues[NutritionColumnIndex.SATURATED_FAT],
            carbohydrates = nutritionValues[NutritionColumnIndex.CARBOHYDRATES],
        )
    }

}