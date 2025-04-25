package domain.use_case.fetch

import data.meal.MealsRepository
import domain.use_case.createMeal
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
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
        every { mealsRepository.getAllMeals() } returns emptyList()

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertEquals(emptyList(), result)
    }

    @Test
    fun `should return list of iraqi meals when tagged by italy`() {
        val iraqiMeals = listOf(
            createMeal(tag = listOf("iraqi",""), description = ""),
            createMeal(tag = listOf("","iraqi",""), description = "")
        )
        every { mealsRepository.getAllMeals() } returns iraqiMeals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertEquals(iraqiMeals, result)
    }

    @Test
    fun `should return list of iraqi meals when description contain Iraq`() {
        val iraqiMeals = listOf(
            createMeal(tag = listOf("",""), description = "Iraq"),
            createMeal(tag = listOf("",""), description = "Iraq")
        )
        every { mealsRepository.getAllMeals() } returns iraqiMeals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertEquals(iraqiMeals, result)
    }

    @Test
    fun `should return list of iraqi meals when tagged by iraqi and description contain Iraq`() {
        val iraqiMeals = listOf(
            createMeal(tag = listOf("iraqi","",""), description = "Iraq"),
            createMeal(tag = listOf("","iraqi"), description = "Iraq")
        )
        every { mealsRepository.getAllMeals() } returns iraqiMeals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertEquals(iraqiMeals, result)
    }

    @Test
    fun `should return empty list when no meals match`() {
        val meals = listOf (
            createMeal(tag = listOf("","italian",""), description = ""),
            createMeal(tag = listOf("","cairo"), description = ""),
            createMeal(tag = listOf(""), description = "From London")
        )
        every { mealsRepository.getAllMeals() } returns meals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertEquals(emptyList(), result)
    }

    @Test
    fun `should return meals list when enter lower and upper case`() {
        val meals = listOf(
            createMeal(tag = listOf("","Iraqi"), description = "iraq"),
            createMeal(tag = listOf("IRAQ",""), description = "IRAQI"),
            createMeal(tag = listOf("Iraq","",""), description = "Iraqi")
        )
        every { mealsRepository.getAllMeals() } returns meals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertEquals(meals, result)
    }

    @Test
    fun `should return empty list when description is null`() {
        val meals = listOf(
            createMeal(tag = listOf(""), description = null),
        )
        every { mealsRepository.getAllMeals() } returns meals

        val result = getIraqiMealsUseCase.getIraqiMeals()

        assertEquals(emptyList(), result)
    }
}