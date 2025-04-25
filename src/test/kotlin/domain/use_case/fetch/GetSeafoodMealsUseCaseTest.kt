package domain.use_case.fetch

import data.meal.MealsRepository
import data.model.Meal
import data.model.Nutrition
import domain.NoMealsFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertFailsWith

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
        val shrimp = createMeal("Shrimp", 1, listOf("Seafood"), 35.0)
        val fish = createMeal("Fish", 2, listOf("seafood"), 25.0)
        val chicken = createMeal("Chicken", 3, listOf("Meat"), 30.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish, chicken)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(2, result.size)
    }

    @Test
    fun `getSeafoodMeals sorts seafood meals with highest protein first`() {
        val shrimp = createMeal("Shrimp", 1, listOf("Seafood"), 35.0)
        val fish = createMeal("Fish", 2, listOf("seafood"), 25.0)
        val chicken = createMeal("Chicken", 3, listOf("Meat"), 30.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish, chicken)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals("Shrimp", result[0].name)
    }

    @Test
    fun `getSeafoodMeals assigns correct protein to first seafood meal`() {
        val shrimp = createMeal("Shrimp", 1, listOf("Seafood"), 35.0)
        val fish = createMeal("Fish", 2, listOf("seafood"), 25.0)
        val chicken = createMeal("Chicken", 3, listOf("Meat"), 30.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish, chicken)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(35.0, result[0].protein)
    }

    @Test
    fun `getSeafoodMeals sorts seafood meals with lower protein second`() {
        val shrimp = createMeal("Shrimp", 1, listOf("Seafood"), 35.0)
        val fish = createMeal("Fish", 2, listOf("seafood"), 25.0)
        val chicken = createMeal("Chicken", 3, listOf("Meat"), 30.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish, chicken)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals("Fish", result[1].name)
    }

    @Test
    fun `getSeafoodMeals assigns correct protein to second seafood meal`() {
        val shrimp = createMeal("Shrimp", 1, listOf("Seafood"), 35.0)
        val fish = createMeal("Fish", 2, listOf("seafood"), 25.0)
        val chicken = createMeal("Chicken", 3, listOf("Meat"), 30.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish, chicken)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(25.0, result[1].protein)
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
        val chicken = createMeal("Chicken", 1, listOf("Meat"))
        val beef = createMeal("Beef", 2, listOf("Meat"))

        every { mealsRepository.getAllMeals() } returns listOf(chicken, beef)

        assertFailsWith<NoMealsFoundException> {
            getSeafoodMealsUseCase.getSeafoodMeals()
        }
    }

    @Test
    fun `getSeafoodMeals returns correct number of meals with case-insensitive tags`() {
        val shrimp = createMeal("Shrimp", 1, listOf("SEAFOOD"), 30.0)
        val fish = createMeal("Fish", 2, listOf("SeaFood"), 20.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(2, result.size)
    }

    @Test
    fun `getSeafoodMeals sorts case-insensitive seafood meals by protein descending`() {
        val shrimp = createMeal("Shrimp", 1, listOf("SEAFOOD"), 30.0)
        val fish = createMeal("Fish", 2, listOf("SeaFood"), 20.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals("Shrimp", result[0].name)
    }

    @Test
    fun `getSeafoodMeals includes case-insensitive seafood meals in correct order`() {
        val shrimp = createMeal("Shrimp", 1, listOf("SEAFOOD"), 30.0)
        val fish = createMeal("Fish", 2, listOf("SeaFood"), 20.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals("Fish", result[1].name)
    }

    @Test
    fun `getSeafoodMeals returns correct number of meals with same protein`() {
        val shrimp = createMeal("Shrimp", 1, listOf("Seafood"), 25.0)
        val fish = createMeal("Fish", 2, listOf("Seafood"), 25.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(2, result.size)
    }

    @Test
    fun `getSeafoodMeals includes all meals with same protein`() {
        val shrimp = createMeal("Shrimp", 1, listOf("Seafood"), 25.0)
        val fish = createMeal("Fish", 2, listOf("Seafood"), 25.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertTrue(result.map { it.name }.containsAll(listOf("Shrimp", "Fish")))
    }

    @Test
    fun `getSeafoodMeals assigns correct protein to first meal with same protein`() {
        val shrimp = createMeal("Shrimp", 1, listOf("Seafood"), 25.0)
        val fish = createMeal("Fish", 2, listOf("Seafood"), 25.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(25.0, result[0].protein)
    }

    @Test
    fun `getSeafoodMeals assigns correct protein to second meal with same protein`() {
        val shrimp = createMeal("Shrimp", 1, listOf("Seafood"), 25.0)
        val fish = createMeal("Fish", 2, listOf("Seafood"), 25.0)

        every { mealsRepository.getAllMeals() } returns listOf(shrimp, fish)

        val result = getSeafoodMealsUseCase.getSeafoodMeals()

        assertEquals(25.0, result[1].protein)
    }

}