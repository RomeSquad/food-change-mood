package data

import data.utils.CsvParser
import data.utils.MealColumnIndex
import data.utils.NutritionColumnIndex
import model.Meal
import logic.MealsRepository
import model.Nutrition
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat

class CsvMealsRepository(
    private val csvFile: File,
    private val csvParser: CsvParser
) : MealsRepository {

    private val meals by lazy { loadMeals() }
    override fun getAllMeals() = meals


    private fun loadMeals(): List<Meal> {
        if (!csvFile.exists()) {
            throw FileNotFoundException(
                "${csvFile.absolutePath} does not exist"
            )
        }
        val csvLines = csvFile.readLines()
        return parseRecords(csvParser.parseCsv(csvLines.joinToString("\n")))
    }

    private fun parseRecords(rows: List<List<String>>): List<Meal> {
        val meals = mutableListOf<Meal>()
        val dateFormat = SimpleDateFormat("m/d/yyy")

        for ((index, meal) in rows.drop(1).withIndex()) {
            try {
                if (meal.size < 12) {
                    println("Skipping row ${index + 1}: Expected at least 12 fields, got ${meal.size}")
                    continue
                }
                val meal = createMealFromRow(meal, dateFormat)
                meals.add(meal)
            } catch (e: Exception) {
                println("Error parsing row ${index + 1}: ${e.message}")
                continue
            }
        }
        return meals
    }

    private fun createMealFromRow(
        row: List<String>,
        dateFormat: SimpleDateFormat,
    ): Meal {
        val nutritionValues = csvParser.parseDoubleList(row[MealColumnIndex.NUTRITION])

        if (nutritionValues.size < 7) {
            throw IllegalArgumentException("Invalid nutrition data: ${row[MealColumnIndex.NUTRITION]}")
        }
        val nutrition = createNutritionFromValues(nutritionValues)
        return Meal(
            name = row[MealColumnIndex.NAME].trim(),
            id = row[MealColumnIndex.ID].toIntOrNull() ?: throw IllegalArgumentException("Invalid Meal ID"),
            minutes = row[MealColumnIndex.MINUTES].toIntOrNull()
                ?: throw IllegalArgumentException("Invalid Minutes Number"),
            contributorId = row[MealColumnIndex.CONTRIBUTOR_ID].toIntOrNull()
                ?: throw IllegalArgumentException("Invalid Contributor ID"),
            submitted = dateFormat.parse(row[MealColumnIndex.SUBMITTED]),
            tags = csvParser.parseStringList(row[MealColumnIndex.TAGS]),
            nutrition = nutrition,
            nSteps = row[MealColumnIndex.N_STEPS].toIntOrNull() ?: throw IllegalArgumentException("Invalid N Steps"),
            steps = csvParser.parseStringList(row[MealColumnIndex.STEPS]),
            description = row[MealColumnIndex.DESCRIPTION].trim().takeIf { it.isNotEmpty() },
            ingredients = csvParser.parseStringList(row[MealColumnIndex.INGREDIENTS]),
            nIngredients = row[MealColumnIndex.N_INGREDIENTS].toIntOrNull()
                ?: throw IllegalArgumentException("Invalid n_ingredients"),
        )
    }

    private fun createNutritionFromValues(values: List<Double>) =
        Nutrition(
            calories = values[NutritionColumnIndex.CALORIES],
            totalFat = values[NutritionColumnIndex.TOTAL_FAT],
            sugar = values[NutritionColumnIndex.SUGAR],
            sodium = values[NutritionColumnIndex.SODIUM],
            protein = values[NutritionColumnIndex.PROTEIN],
            saturatedFat = values[NutritionColumnIndex.SATURATED_FAT],
            carbohydrates = values[NutritionColumnIndex.CARBOHYDRATES],
        )

}