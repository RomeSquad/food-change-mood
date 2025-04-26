package domain.use_case.search

import data.meal.MealsRepository
import data.model.Meal
import data.model.Nutrition
import domain.use_case.search.utils.SearchAlgorithm
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Date
import kotlin.test.assertEquals

class SearchMealsByNameUseCaseTest {

    private lateinit var repository: MealsRepository

    private lateinit var searchAlgorithm: SearchAlgorithm

    private lateinit var useCase: SearchMealsByNameUseCase

    private val mockMeal = Meal(
        id = 1,
        name = "Chicken Curry",
        minutes = 30,
        contributorId = 100,
        submitted = Date(),
        tags = listOf("spicy", "asian"),
        nutrition = Nutrition(500.0, 20.0, 10.0, 800.0, 30.0, 8.0, 50.0),
        stepsCount = 5,
        steps = listOf("Step 1", "Step 2"),
        description = "Delicious curry",
        ingredients = listOf("chicken", "curry powder"),
        ingredientsCount = 2
    )

    @BeforeEach
    fun setup() {
        repository = mockk()
        searchAlgorithm = mockk()
        useCase = SearchMealsByNameUseCase(repository, searchAlgorithm)
    }

    @Test
    fun `given empty query when searching then return failure result`() {
        // given
        val query = ""
        every { repository.getAllMeals() } returns emptyList()
        every { searchAlgorithm.search(any(), query) } returns Result.failure(Exception("Query must not be empty"))

        // when
        val result = useCase.searchMealsByName(query)

        // then
        TestCase.assertTrue(result.isFailure)
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
        val result = useCase.searchMealsByName(query)

        // then
        TestCase.assertTrue(result.isSuccess)
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
        val result = useCase.searchMealsByName(query)

        // then
        TestCase.assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals(meals, result.getOrNull())
    }

}