package domain.use_case.search

import data.meal.MealsRepository
import data.model.Meal
import data.model.Nutrition
import data.model.gym_helper.CaloriesAndProteinTolerance
import data.model.gym_helper.GymHelperInput
import domain.NoMealsFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import java.util.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

class SearchGymHelperMealsUseCaseTest {
    private lateinit var mealsRepository: MealsRepository
    private lateinit var searchGymHelperMeals: SearchGymHelperMealsUseCase

    private fun createMeal(
        name: String,
        id: Int,
        calories: Double,
        protein: Double
    ) = Meal(
        name = name,
        id = id,
        minutes = 30,
        contributorId = id,
        submitted = Date(),
        tags = emptyList(),
        nutrition = Nutrition(
            calories = calories,
            totalFat = 20.0,
            sugar = 5.0,
            sodium = 800.0,
            protein = protein,
            saturatedFat = 10.0,
            carbohydrates = 50.0
        ),
        stepsCount = 3,
        steps = listOf("Step 1", "Step 2", "Step 3"),
        description = "$name meal",
        ingredients = listOf(name.lowercase()),
        ingredientsCount = 1
    )

    @BeforeEach
    fun setUp() {
        mealsRepository = mockk()
        searchGymHelperMeals = SearchGymHelperMealsUseCase(mealsRepository)
    }

    @Test
    fun `getMealsByCaloriesAndProtein returns correct number of matching meals within tolerance`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(50, 5)
        )
        val meal1 = createMeal("Meal 1", 1, 480.0, 23.0)
        val meal2 = createMeal("Meal 2", 2, 520.0, 27.0)
        val meal3 = createMeal("Meal 3", 3, 600.0, 30.0)
        every { mealsRepository.getAllMeals() } returns listOf(meal1, meal2, meal3)

        val result = searchGymHelperMeals.getMealsByCaloriesAndProtein(input)

        assertEquals(2, result.size)
    }

    @Test
    fun `getMealsByCaloriesAndProtein includes all matching meals within tolerance`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(50, 5)
        )
        val meal1 = createMeal("Meal 1", 1, 480.0, 23.0)
        val meal2 = createMeal("Meal 2", 2, 520.0, 27.0)
        val meal3 = createMeal("Meal 3", 3, 600.0, 30.0)
        every { mealsRepository.getAllMeals() } returns listOf(meal1, meal2, meal3)

        val result = searchGymHelperMeals.getMealsByCaloriesAndProtein(input)

        assertTrue(result.map { it.name }.containsAll(listOf("Meal 1", "Meal 2")))
    }

    @Test
    fun `getMealsByCaloriesAndProtein throws NoMealsFoundException when no matches found`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(10, 1)
        )
        val meal1 = createMeal("Meal 1", 1, 600.0, 30.0)
        every { mealsRepository.getAllMeals() } returns listOf(meal1)

        assertFailsWith<NoMealsFoundException> {
            searchGymHelperMeals.getMealsByCaloriesAndProtein(input)
        }
    }

    @Test
    fun `getMealsByCaloriesAndProtein throws NoMealsFoundException with correct message when no matches found`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(10, 1)
        )
        val meal1 = createMeal("Meal 1", 1, 600.0, 30.0)
        every { mealsRepository.getAllMeals() } returns listOf(meal1)

        val exception = assertFailsWith<NoMealsFoundException> {
            searchGymHelperMeals.getMealsByCaloriesAndProtein(input)
        }
        assertEquals(
            "No meals found matching calories = 500.0 ± 10 and protein = 25.0 ± 1",
            exception.message
        )
    }

    @Test
    fun `getMealsByCaloriesAndProtein throws IllegalArgumentException for negative calories tolerance`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(-10, 5)
        )

        assertFailsWith<IllegalArgumentException> {
            searchGymHelperMeals.getMealsByCaloriesAndProtein(input)
        }
    }

    @Test
    fun `getMealsByCaloriesAndProtein throws IllegalArgumentException with correct message for negative calories tolerance`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(-10, 5)
        )

        val exception = assertFailsWith<IllegalArgumentException> {
            searchGymHelperMeals.getMealsByCaloriesAndProtein(input)
        }
        assertEquals("Calories tolerance must be a non-negative integer.", exception.message)
    }

    @Test
    fun `getMealsByCaloriesAndProtein throws IllegalArgumentException for negative protein tolerance`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(50, -5)
        )

        assertFailsWith<IllegalArgumentException> {
            searchGymHelperMeals.getMealsByCaloriesAndProtein(input)
        }
    }

    @Test
    fun `getMealsByCaloriesAndProtein throws IllegalArgumentException with correct message for negative protein tolerance`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(50, -5)
        )

        val exception = assertFailsWith<IllegalArgumentException> {
            searchGymHelperMeals.getMealsByCaloriesAndProtein(input)
        }
        assertEquals("Protein tolerance must be a non-negative integer.", exception.message)
    }

    @Test
    fun `getMealsByCaloriesAndProtein throws NoMealsFoundException for empty meal list`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(50, 5)
        )
        every { mealsRepository.getAllMeals() } returns emptyList()

        assertFailsWith<NoMealsFoundException> {
            searchGymHelperMeals.getMealsByCaloriesAndProtein(input)
        }
    }

    @Test
    fun `getMealsByCaloriesAndProtein returns correct number of meals for exact match`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(0, 0)
        )
        val meal1 = createMeal("Meal 1", 1, 500.0, 25.0)
        val meal2 = createMeal("Meal 2", 2, 500.1, 25.1)
        every { mealsRepository.getAllMeals() } returns listOf(meal1, meal2)

        val result = searchGymHelperMeals.getMealsByCaloriesAndProtein(input)

        assertEquals(1, result.size)
    }

    @Test
    fun `getMealsByCaloriesAndProtein returns correct meal name for exact match`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(0, 0)
        )
        val meal1 = createMeal("Meal 1", 1, 500.0, 25.0)
        val meal2 = createMeal("Meal 2", 2, 500.1, 25.1)
        every { mealsRepository.getAllMeals() } returns listOf(meal1, meal2)

        val result = searchGymHelperMeals.getMealsByCaloriesAndProtein(input)

        assertEquals("Meal 1", result[0].name)
    }

    @Test
    fun `getMealsByCaloriesAndProtein returns correct number of meals when filtering protein out of range`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(50, 5)
        )
        val meal1 = createMeal("Meal 1", 1, 480.0, 23.0)
        val meal2 = createMeal("Meal 2", 2, 490.0, 35.0)
        every { mealsRepository.getAllMeals() } returns listOf(meal1, meal2)

        val result = searchGymHelperMeals.getMealsByCaloriesAndProtein(input)

        assertEquals(1, result.size)
    }

    @Test
    fun `getMealsByCaloriesAndProtein returns correct meal name when filtering protein out of range`() {
        val input = GymHelperInput(
            calories = 500.0,
            protein = 25.0,
            caloriesAndProteinTolerance = CaloriesAndProteinTolerance(50, 5)
        )
        val meal1 = createMeal("Meal 1", 1, 480.0, 23.0)
        val meal2 = createMeal("Meal 2", 2, 490.0, 35.0)
        every { mealsRepository.getAllMeals() } returns listOf(meal1, meal2)

        val result = searchGymHelperMeals.getMealsByCaloriesAndProtein(input)

        assertEquals("Meal 1", result[0].name)
    }

}

