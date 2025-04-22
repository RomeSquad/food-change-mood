package domain.use_case

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import data.meal.MealsRepository
import model.Meal
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Date

class GetHealthyMealsFilterUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var getHealthyMealsFilterUseCase: GetHealthyMealsFilterUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk()
        getHealthyMealsFilterUseCase = GetHealthyMealsFilterUseCase(mealsRepository)
    }
    @Test
    fun `should return only healthy meals under 15 minutes `() {
        val meals = listOf(
            createMeal(name = "Salad", minutes = 10, totalFat = 4.0, saturatedFat = 2.0, carbohydrates = 10.0),
            createMeal(name = "Burger", minutes = 20, totalFat = 30.0, saturatedFat = 15.0, carbohydrates = 40.0)
        )
        //Given
        every { mealsRepository.getAllMeals() } returns meals
        //when
        val result = getHealthyMealsFilterUseCase.getHealthyFastMeals()
        //then
        assertThat(result.map { it.name }).contains("Salad")
        assertThat(result.map { it.name }).doesNotContain("Burger")
    }
    // Helper Method
    private fun createMeal(
        id: Int = 0,
        name: String,
        minutes: Int,
        totalFat: Double,
        saturatedFat: Double,
        carbohydrates: Double
    ): Meal {
        return Meal(
            id = id,
            name = name,
            description = "Test meal",
            contributorId = 0,
            ingredients = listOf(),
            nIngredients = 0,
            steps = listOf(),
            nSteps = 0,
            submitted = Date(),
            tags = listOf(),
            minutes = minutes,
            nutrition = Nutrition(
                calories = 0.0,
                totalFat = totalFat,
                sugar = 0.0,
                sodium = 0.0,
                protein = 0.0,
                saturatedFat = saturatedFat,
                carbohydrates = carbohydrates
            )
        )
    }
}