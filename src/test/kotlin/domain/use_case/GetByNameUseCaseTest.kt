package domain.use_case

import data.meal.MealsRepository
import domain.search.SearchAlgorithm
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import model.Meal
import model.Nutrition
import org.junit.Ignore
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals


class GetByNameUseCaseTest {

    private lateinit var repository: MealsRepository

    private lateinit var searchAlgorithm: SearchAlgorithm

    private lateinit var useCase: GetByNameUseCase

    private val mockMeal = Meal(
        id = 1,
        name = "Chicken Curry",
        minutes = 30,
        contributorId = 100,
        submitted = Date(),
        tags = listOf("spicy", "asian"),
        nutrition = Nutrition(500.0, 20.0, 10.0, 800.0, 30.0, 8.0, 50.0),
        nSteps = 5,
        steps = listOf("Step 1", "Step 2"),
        description = "Delicious curry",
        ingredients = listOf("chicken", "curry powder"),
        nIngredients = 2
    )

    @BeforeEach
    fun setup() {
        repository = mockk()
        searchAlgorithm = mockk()
        useCase = GetByNameUseCase(repository, searchAlgorithm)
    }

    @Test
    fun `given empty query when searching then return failure result`() {
        // given
        val query = ""
        every { repository.getAllMeals() } returns emptyList()
        every { searchAlgorithm.search(any(), query) } returns Result.failure(Exception("Query must not be empty"))

        // when
        val result = useCase.getByName(query)

        // then
        assertTrue(result.isFailure)
        assertEquals("Query must not be empty", result.exceptionOrNull()?.message)
    }

    @Test
    fun `given valid query when meals found then return success result with meals`() {
        // given
        val query = "Chicken"
        val meals = listOf(mockMeal)
        every { repository.getAllMeals() } returns meals
        every { searchAlgorithm.search(meals, query) } returns Result.success(meals)

        // when
        val result = useCase.getByName(query)

        // then
        assertTrue(result.isSuccess)
        assertEquals(meals, result.getOrNull())
    }

    @Test
    fun `given valid query when search algorithm returns partial matches then return matching meals`() {
        // given
        val query = "Chick"
        val meals = listOf(mockMeal)
        every { repository.getAllMeals() } returns listOf(mockMeal, mockMeal.copy(id = 2, name = "Beef Stew"))
        every { searchAlgorithm.search(any(), query) } returns Result.success(meals)

        // when
        val result = useCase.getByName(query)

        // then
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals(meals, result.getOrNull())
    }

}