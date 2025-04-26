package domain.use_case.search

import data.meal.MealsRepository
import data.model.Meal
import data.model.Nutrition
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class SearchFoodByCountryUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var searchFoodByCountryUseCase: SearchFoodByCountryUseCase

    private val mockMeals = listOf(
        createFakeMealsHaveSameCountry(
            name = "iraq",
            tags = listOf("gaza", "asian"),
            steps = listOf("gaza", "Step 2"),
            description = "gaza",
            ingredients = listOf("gaza", "curry powder")
        ),
        createFakeMealsHaveSameCountry(
            name = "gaza",
            tags = listOf("iraq", "asians"),
            steps = listOf("gaza", "Step 2"),
            description = "gaza",
            ingredients = listOf("gaza", "curry powder")
        ),
        createFakeMealsHaveSameCountry(
            name = "gaza",
            tags = listOf("gaza", "asians"),
            steps = listOf("iraq", "Step 2"),
            description = "gaza",
            ingredients = listOf("gaza", "curry powder")
        ),
        createFakeMealsHaveSameCountry(
            name = "gaza",
            tags = listOf("gaza", "asians"),
            steps = listOf("gaza", "Step 2"),
            description = "iraq",
            ingredients = listOf("gaza", "curry powder")
        ),
        createFakeMealsHaveSameCountry(
            name = "gaza",
            tags = listOf("gaza", "asians"),
            steps = listOf("gaza", "Step 2"),
            description = "gaza",
            ingredients = listOf("iraq", "curry powder")
        ),
        createFakeMealsHaveSameCountry(
            name = "gaza",
            tags = listOf("iraq", "asians"),
            steps = listOf("gaza", "Step 2"),
            description = "gaza",
            ingredients = listOf("iraq", "curry powder")
        ),
    )

    @BeforeEach
    fun setup() {
        mealsRepository = mockk()
        searchFoodByCountryUseCase = SearchFoodByCountryUseCase(mealsRepository)
    }

    @Test
    fun `given meals when searching about the country then return success`() {
        val countryName = "iraq"
        //Given
        every { mealsRepository.getAllMeals() } returns mockMeals

        //When
        val result = searchFoodByCountryUseCase.exploreMealsRelatedToCountry(countryName)[0].name

        //Then
        assertEquals("iraq", result)
    }

    @Test
    fun `throws NoSuchElementException when no meals found for given country`() {
        val countryName = "korea"
        // Given
        every { mealsRepository.getAllMeals() } returns mockMeals

        // When & Then
        val exception = org.junit.jupiter.api.assertThrows<NoSuchElementException> {
            searchFoodByCountryUseCase.exploreMealsRelatedToCountry(countryName)
        }

        assertEquals("No meals found related to country: $countryName", exception.message)
    }
}


fun createFakeMealsHaveSameCountry(
    name: String,
    tags: List<String>,
    description: String?,
    ingredients: List<String>,
    steps: List<String>
) = Meal(
    id = 1,
    name = name,
    minutes = 15,
    contributorId = 100,
    submitted = Date(),
    tags = tags,
    nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
    stepsCount = 5,
    steps = steps,
    description = description,
    ingredients = ingredients,
    ingredientsCount = 2
)
