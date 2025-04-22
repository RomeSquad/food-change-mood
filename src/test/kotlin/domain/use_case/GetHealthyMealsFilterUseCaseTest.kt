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