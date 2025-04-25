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
import java.util.Date
import kotlin.test.assertFailsWith

class GetMealsContainsPotatoUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase

    @BeforeEach
    fun setUp() {
        mealsRepository = mockk(relaxed = true)
        getMealsContainsPotatoUseCase = GetMealsContainsPotatoUseCase(mealsRepository)
    }

    private fun createMeal(
        name: String,
        id: Int,
        ingredients: List<String>
    ): Meal {
        return Meal(
            name = name,
            id = id,
            minutes = 30,
            contributorId = id,
            submitted = Date(),
            tags = emptyList(),
            nutrition = Nutrition(
                calories = 500.0,
                totalFat = 20.0,
                sugar = 5.0,
                sodium = 800.0,
                protein = 25.0,
                saturatedFat = 10.0,
                carbohydrates = 50.0
            ),
            stepsCount = 3,
            steps = listOf("Step 1", "Step 2", "Step 3"),
            description = "$name meal",
            ingredients = ingredients,
            ingredientsCount = ingredients.size
        )
    }

    @Test
    fun `getMealsContainsPotato returns correct number of potato meals when limited`() {
        val potatoPie = createMeal("Potato Pie", 1, listOf("potato", "flour"))
        val mash = createMeal("Mashed Potatoes", 2, listOf("Potatoes", "butter"))
        val chicken = createMeal("Chicken Roast", 3, listOf("chicken", "salt"))

        every { mealsRepository.getAllMeals() } returns listOf(potatoPie, mash, chicken)

        val result = getMealsContainsPotatoUseCase.getMealsContainsPotato(2)

        assertEquals(2, result.size)
    }

    @Test
    fun `getMealsContainsPotato returns only potato meals when limited`() {
        val potatoPie = createMeal("Potato Pie", 1, listOf("potato", "flour"))
        val mash = createMeal("Mashed Potatoes", 2, listOf("Potatoes", "butter"))
        val chicken = createMeal("Chicken Roast", 3, listOf("chicken", "salt"))

        every { mealsRepository.getAllMeals() } returns listOf(potatoPie, mash, chicken)

        val result = getMealsContainsPotatoUseCase.getMealsContainsPotato(2)

        assertTrue(result.all { it in listOf("Potato Pie", "Mashed Potatoes") })
    }

    @Test
    fun `getMealsContainsPotato throws NoSuchElementException when no potato meals found`() {
        val chicken = createMeal("Chicken Roast", 1, listOf("chicken", "salt"))
        val beef = createMeal("Beef Stew", 2, listOf("beef", "carrots"))

        every { mealsRepository.getAllMeals() } returns listOf(chicken, beef)

        assertFailsWith<NoMealsFoundException> {
            getMealsContainsPotatoUseCase.getMealsContainsPotato()
        }
    }

    @Test
    fun `getMealsContainsPotato returns correct number of meals with case-insensitive matching`() {
        val potatoSoup = createMeal("Potato Soup", 1, listOf("POTATO", "water"))
        val mash = createMeal("Mashed Potatoes", 2, listOf("pOtAtOeS", "butter"))
        val fries = createMeal("French Fries", 3, listOf("potatoes", "oil"))

        every { mealsRepository.getAllMeals() } returns listOf(potatoSoup, mash, fries)

        val result = getMealsContainsPotatoUseCase.getMealsContainsPotato(2)

        assertEquals(2, result.size)
    }

    @Test
    fun `getMealsContainsPotato returns case-insensitive potato meal names`() {
        val potatoSoup = createMeal("Potato Soup", 1, listOf("POTATO", "water"))
        val mash = createMeal("Mashed Potatoes", 2, listOf("pOtAtOeS", "butter"))
        val fries = createMeal("French Fries", 3, listOf("potatoes", "oil"))

        every { mealsRepository.getAllMeals() } returns listOf(potatoSoup, mash, fries)

        val result = getMealsContainsPotatoUseCase.getMealsContainsPotato(2)

        assertTrue(result.all { it in listOf("Potato Soup", "Mashed Potatoes", "French Fries") })
    }

    @Test
    fun `getMealsContainsPotato returns empty list when limit is zero`() {
        val potatoPie = createMeal("Potato Pie", 1, listOf("potato", "flour"))
        every { mealsRepository.getAllMeals() } returns listOf(potatoPie)

        val result = getMealsContainsPotatoUseCase.getMealsContainsPotato(0)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getMealsContainsPotato returns correct number of meals when limit exceeds count`() {
        val potatoPie = createMeal("Potato Pie", 1, listOf("potato", "flour"))
        val mash = createMeal("Mashed Potatoes", 2, listOf("potatoes", "butter"))

        every { mealsRepository.getAllMeals() } returns listOf(potatoPie, mash)

        val result = getMealsContainsPotatoUseCase.getMealsContainsPotato(5)

        assertEquals(2, result.size)
    }

    @Test
    fun `getMealsContainsPotato returns all potato meals when limit exceeds count`() {
        val potatoPie = createMeal("Potato Pie", 1, listOf("potato", "flour"))
        val mash = createMeal("Mashed Potatoes", 2, listOf("potatoes", "butter"))

        every { mealsRepository.getAllMeals() } returns listOf(potatoPie, mash)

        val result = getMealsContainsPotatoUseCase.getMealsContainsPotato(5)

        assertTrue(result.containsAll(listOf("Potato Pie", "Mashed Potatoes")))
    }
}