package domain.use_case.fetch

import data.meal.MealsRepository
import data.model.Meal
import data.model.Nutrition
import domain.NoMealsFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertFailsWith

class GetSeafoodMealsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var getSeafoodMealsUseCase: GetSeafoodMealsUseCase

    private val seafoodMeals = listOf(
        createMeal("Shrimp", 1, listOf("Seafood"), 35.0),
        createMeal("Fish", 2, listOf("seafood"), 25.0)
    )

    private val nonSeafoodMeals = listOf(
        createMeal("Chicken", 3, listOf("Meat"), 30.0),
        createMeal("Beef", 4, listOf("Meat"), 40.0)
    )

    private val mixedMeals = seafoodMeals + nonSeafoodMeals

    private val sameProteinMeals = listOf(
        createMeal("Shrimp", 1, listOf("Seafood"), 25.0),
        createMeal("Fish", 2, listOf("Seafood"), 25.0)
    )

    private val caseInsensitiveMeals = listOf(
        createMeal("Shrimp", 1, listOf("SEAFOOD"), 30.0),
        createMeal("Fish", 2, listOf("SeaFood"), 20.0)
    )

    @BeforeEach
    fun setUp() {
        mealsRepository = mockk()
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
            nutrition = Nutrition(
                calories = 500.0,
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
            ingredients = listOf(name.lowercase(), "salt"),
            ingredientsCount = 2
        )
    }

    @Test
    fun `getSeafoodMeals returns correct number of seafood meals`() {
        every { mealsRepository.getAllMeals() } returns mixedMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(2, result.size)
    }

    @Test
    fun `getSeafoodMeals sorts seafood meals with highest protein first`() {
        every { mealsRepository.getAllMeals() } returns mixedMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals("Shrimp", result[0].name)
    }

    @Test
    fun `getSeafoodMeals has highest protein value in first meal`() {
        every { mealsRepository.getAllMeals() } returns mixedMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(35.0, result[0].proteinPerGram)
    }

    @Test
    fun `getSeafoodMeals sorts seafood meals with lower protein second`() {
        every { mealsRepository.getAllMeals() } returns mixedMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals("Fish", result[1].name)
    }

    @Test
    fun `getSeafoodMeals has second highest protein value in second meal`() {
        every { mealsRepository.getAllMeals() } returns mixedMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(25.0, result[1].proteinPerGram)
    }

    @Test
    fun `getSeafoodMeals throws NoMealsFoundException when repository returns empty list`() {
        every { mealsRepository.getAllMeals() } returns emptyList()

        assertFailsWith<NoMealsFoundException> {
            getSeafoodMealsUseCase.getSeafoodMeals()
        }
    }

    @Test
    fun `getSeafoodMeals throws NoMealsFoundException when no seafood meals are found`() {
        every { mealsRepository.getAllMeals() } returns nonSeafoodMeals

        assertFailsWith<NoMealsFoundException> {
            getSeafoodMealsUseCase.getSeafoodMeals()
        }
    }

    @Test
    fun `getSeafoodMeals returns correct number of meals with case-insensitive tags`() {
        every { mealsRepository.getAllMeals() } returns caseInsensitiveMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(2, result.size)
    }

    @Test
    fun `getSeafoodMeals sorts case-insensitive seafood meals by protein descending`() {
        every { mealsRepository.getAllMeals() } returns caseInsensitiveMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals("Shrimp", result[0].name)
    }

    @Test
    fun `getSeafoodMeals includes case-insensitive seafood meals in correct order`() {
        every { mealsRepository.getAllMeals() } returns caseInsensitiveMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals("Fish", result[1].name)
    }

    @Test
    fun `getSeafoodMeals returns correct number of meals with same protein`() {
        every { mealsRepository.getAllMeals() } returns sameProteinMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(2, result.size)
    }

    @Test
    fun `getSeafoodMeals includes all meals with same protein`() {
        every { mealsRepository.getAllMeals() } returns sameProteinMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertTrue(result.map { it.name }.containsAll(listOf("Shrimp", "Fish")))
    }

    @Test
    fun `getSeafoodMeals assigns correct protein to first meal with same protein`() {
        every { mealsRepository.getAllMeals() } returns sameProteinMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(25.0, result[0].proteinPerGram)
    }

    @Test
    fun `getSeafoodMeals assigns correct protein to second meal with same protein`() {
        every { mealsRepository.getAllMeals() } returns sameProteinMeals

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(25.0, result[1].proteinPerGram)
    }
}
