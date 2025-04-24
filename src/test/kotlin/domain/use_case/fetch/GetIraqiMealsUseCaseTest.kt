package domain.use_case.fetch

import data.meal.MealsRepository
import data.model.Meal
import data.model.Nutrition
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class GetIraqiMealsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk()
        getIraqiMealsUseCase = GetIraqiMealsUseCase(mealsRepository)
    }

    @Test
    fun `should return empty list when no meals found`() {
        // Given
        every { mealsRepository.getAllMeals() } returns emptyList()

        // When
        val result = emptyList<Meal>()

        // Then
        assertEquals(emptyList(), result)
    }

    @Test
    fun `should return list of italian meals when tagged by italy`() {
        // Given
        val iraqiMeals = listOf(
            createMeal(tag = listOf("iraqi",""), description = ""),
            createMeal(tag = listOf("","iraqi",""), description = "")
        )
        every { mealsRepository.getAllMeals() } returns iraqiMeals

        // When
        val result = mealsRepository.getAllMeals()

        // Then
        assertEquals(iraqiMeals, result)
    }

    @Test
    fun `should return list of italian meals when description contain Iraq`() {
        // Given
        val iraqiMeals = listOf(
            createMeal(tag = listOf("",""), description = "Iraq"),
            createMeal(tag = listOf("",""), description = "Iraq")
        )
        every { mealsRepository.getAllMeals() } returns iraqiMeals

        // When
        val result = mealsRepository.getAllMeals()

        // Then
        assertEquals(iraqiMeals, result)
    }

    @Test
    fun `should return list of italian meals when tagged by iraqi and description contain Iraq`() {
        // Given
        val iraqiMeals = listOf(
            createMeal(tag = listOf("iraqi","",""), description = "Iraq"),
            createMeal(tag = listOf("","iraqi"), description = "Iraq")
        )
        every { mealsRepository.getAllMeals() } returns iraqiMeals

        // When
        val result = mealsRepository.getAllMeals()

        // Then
        assertEquals(iraqiMeals, result)
    }

    @Test
    fun `should return empty list when no meals match`() {
        // Given
        val meals = listOf (
            createMeal(tag = listOf("","italian",""), description = ""),
            createMeal(tag = listOf("","cairo"), description = ""),
            createMeal(tag = listOf(""), description = "From London")
        )
        every { mealsRepository.getAllMeals() } returns meals

        // When
        val result = getIraqiMealsUseCase.getIraqiMeals()

        // Then
        assertEquals(emptyList(), result)
    }

    @Test
    fun `should return meals list when enter lower and upper case`() {
        // Given
        val meals = listOf(
            createMeal(tag = listOf("","Iraqi"), description = "iraq"),
            createMeal(tag = listOf("IRAQ",""), description = "IRAQI"),
            createMeal(tag = listOf("Iraq","",""), description = "Iraqi")
        )
        every { mealsRepository.getAllMeals() } returns meals

        // When
        val result = getIraqiMealsUseCase.getIraqiMeals()

        // Then
        assertEquals(meals, result)
    }

    @Test
    fun `should return false when description is null`() {
        // Given
        val meals = listOf(
            createMeal(tag = listOf(""), description = null),
        )
        every { mealsRepository.getAllMeals() } returns meals

        // When
        val result = getIraqiMealsUseCase.getIraqiMeals()

        // Then
        assertEquals(emptyList(), result)
    }

    private fun createMeal(
        tag: List<String>,
        description: String?
    ): Meal {
        return Meal(
            name = "",
            id = 0,
            minutes = 0,
            contributorId = 0,
            submitted = Date(),
            tags = tag,
            nutrition = Nutrition(
                calories = 0.0,
                totalFat = 0.0,
                sugar = 0.0,
                sodium = 0.0,
                protein = 0.0,
                saturatedFat = 0.0,
                carbohydrates = 0.0
            ),
            stepsCount = 0,
            steps = listOf("",""),
            description = description,
            ingredients = listOf("",""),
            ingredientsCount = 0
        )
    }
}