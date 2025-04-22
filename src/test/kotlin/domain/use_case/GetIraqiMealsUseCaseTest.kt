package domain.use_case

import data.meal.MealsRepository
import io.mockk.every
import io.mockk.mockk
import model.Meal
import model.Nutrition
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.util.Date
import kotlin.test.Test

class GetIraqiMealsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk()
        getIraqiMealsUseCase = GetIraqiMealsUseCase(mealsRepository)
    }

    @Test
    fun `should throw exception when no iraqi meals found`() {
        // Given
        every { mealsRepository.getAllMeals() } returns emptyList()

        // When
        val result = getIraqiMealsUseCase.getIraqiMeals()

        // When & Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return iraqi meals tagged by iraqi`() {
        // Given
        val iraqiMeals = listOf(
            Meal(
                name = "",
                id = 0,
                minutes = 0,
                contributorId = 0,
                submitted = Date(),
                tags = listOf("","iraqi"),
                nutrition = Nutrition(
                    calories = 0.0,
                    totalFat = 0.0,
                    sugar = 0.0,
                    sodium = 0.0,
                    protein = 0.0,
                    saturatedFat = 0.0,
                    carbohydrates = 0.0
                ),
                nSteps = 0,
                steps = listOf("",""),
                description = "",
                ingredients = listOf("",""),
                nIngredients = 0,
            ),
            Meal(
                name = "",
                id = 0,
                minutes = 0,
                contributorId = 0,
                submitted = Date(),
                tags = listOf("","iraqi"),
                nutrition = Nutrition(
                    calories = 0.0,
                    totalFat = 0.0,
                    sugar = 0.0,
                    sodium = 0.0,
                    protein = 0.0,
                    saturatedFat = 0.0,
                    carbohydrates = 0.0
                ),
                nSteps = 0,
                steps = listOf("",""),
                description = "",
                ingredients = listOf("",""),
                nIngredients = 0,
            )
        )
        every { mealsRepository.getAllMeals() } returns iraqiMeals

        // When
        val result = getIraqiMealsUseCase.getIraqiMeals()

        // Then
        assertEquals(result, iraqiMeals)
    }

    @Test
    fun `should return Iraq meals when description contain Iraq Text`() {
        // Given
        val iraqMeals = listOf(
            Meal(
                name = "",
                id = 0,
                minutes = 0,
                contributorId = 0,
                submitted = Date(),
                tags = listOf("",""),
                nutrition = Nutrition(
                    calories = 0.0,
                    totalFat = 0.0,
                    sugar = 0.0,
                    sodium = 0.0,
                    protein = 0.0,
                    saturatedFat = 0.0,
                    carbohydrates = 0.0
                ),
                nSteps = 0,
                steps = listOf("",""),
                description = "Iraq",
                ingredients = listOf("",""),
                nIngredients = 0,
            ),
            Meal(
                name = "",
                id = 0,
                minutes = 0,
                contributorId = 0,
                submitted = Date(),
                tags = listOf("",""),
                nutrition = Nutrition(
                    calories = 0.0,
                    totalFat = 0.0,
                    sugar = 0.0,
                    sodium = 0.0,
                    protein = 0.0,
                    saturatedFat = 0.0,
                    carbohydrates = 0.0
                ),
                nSteps = 0,
                steps = listOf("",""),
                description = "Iraq",
                ingredients = listOf("",""),
                nIngredients = 0,
            )
        )
        every { mealsRepository.getAllMeals() } returns iraqMeals

        // When
        val result = getIraqiMealsUseCase.getIraqiMeals()

        // Then
        assertEquals(result, iraqMeals)
    }
}