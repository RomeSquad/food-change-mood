package domain.use_case.fetch

import data.meal.MealsRepository
import data.model.Meal
import data.model.Nutrition
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue


class GetSeafoodMealsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var getSeafoodMealsUseCase: GetSeafoodMealsUseCase

    @BeforeEach
    fun setUp() {
        mealsRepository = mockk(relaxed = true)
        getSeafoodMealsUseCase = GetSeafoodMealsUseCase(mealsRepository)
    }

    private fun createMeal(
        name: String,
        id: Int,
        tags: List<String>,
        protein: Double = 25.0
    ): Meal {
        return Meal(
            name = name,
            id = id,
            minutes = 30,
            contributorId = id,
            submitted = Date(),
            tags = tags,
            nutrition = Nutrition(protein = protein),
            stepsCount = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "$name meal",
            ingredients = listOf(name.lowercase(), "salt"),
            ingredientsCount = 2
        )
    }

    @Test
    fun `should return seafood meals sorted by protein in descending order`() {
        val shrimp = createMeal("Shrimp", 1, listOf("Seafood"), 35.0)
        val fish = createMeal("Fish", 2, listOf("seafood"), 25.0)
        val chicken = createMeal("Chicken", 3, listOf("Meat"), 30.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish, chicken)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(2, result.size)
        assertEquals("Shrimp", result[0].name)
        assertEquals(35.0, result[0].protein)
        assertEquals("Fish", result[1].name)
        assertEquals(25.0, result[1].protein)
    }

    @Test
    fun `should throw NoSuchElementException when repository returns empty list`() {
        every { mealsRepository.getAllMeals() } returns emptyList()

        assertFailsWith<NoSuchElementException> {
            getSeafoodMealsUseCase.getSeafoodMeals()
        }
    }

    @Test
    fun `should throw NoSuchElementException when no seafood meals are found`() {
        val chicken = createMeal("Chicken", 1, listOf("Meat"))
        val beef = createMeal("Beef", 2, listOf("Meat"))

        every { mealsRepository.getAllMeals() } returns listOf(chicken, beef)

        assertFailsWith<NoSuchElementException> {
            getSeafoodMealsUseCase.getSeafoodMeals()
        }
    }

    @Test
    fun `should handle case-insensitive seafood tag matching`() {
        val shrimp = createMeal("Shrimp", 1, listOf("SEAFOOD"), 30.0)
        val fish = createMeal("Fish", 2, listOf("SeaFood"), 20.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(2, result.size)
        assertEquals("Shrimp", result[0].name)
        assertEquals("Fish", result[1].name)
    }

    @Test
    fun `should return sorted seafood meals when multiple meals have the same protein value`() {
        val shrimp = createMeal("Shrimp", 1, listOf("Seafood"), 25.0)
        val fish = createMeal("Fish", 2, listOf("Seafood"), 25.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(2, result.size)
        assertTrue(result.map { it.name }.containsAll(listOf("Shrimp", "Fish")))
        assertEquals(25.0, result[0].protein)
        assertEquals(25.0, result[1].protein)
    }
}