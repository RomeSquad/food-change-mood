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
    fun `should return only healthy meals under 15 minutes`() {
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

    @Test
    fun `should exclude meals with more than 15 minutes preparation time`() {
        val meals = listOf(
            createMeal(name = "Slow Dish", minutes = 18, totalFat = 3.0, saturatedFat = 1.5, carbohydrates = 9.0)
        )
        //Given
        every { mealsRepository.getAllMeals() } returns meals
        //when
        val result = getHealthyMealsFilterUseCase.getHealthyFastMeals()
        //then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should exclude meals with high fat even if under 15 minutes`() {
        val meals = listOf(

            createMeal(name = "Salad", minutes = 10, totalFat = 3.0, saturatedFat = 1.0, carbohydrates = 10.0),
            createMeal(name = "Boiled Veggies", minutes = 12, totalFat = 2.5, saturatedFat = 1.2, carbohydrates = 8.0),
            createMeal(name = "Fruit Bowl", minutes = 9, totalFat = 2.0, saturatedFat = 1.0, carbohydrates = 6.5),

             createMeal(name = "Fried Chicken", minutes = 12, totalFat = 35.0, saturatedFat = 18.0, carbohydrates = 15.0)
        )
        //Given
        every { mealsRepository.getAllMeals() } returns meals
        //when
        val result = getHealthyMealsFilterUseCase.getHealthyFastMeals()
        //then
        assertThat(result.map { it.name }).doesNotContain("Fried Chicken")
    }

    @Test
    fun `should return empty list when no meals are available`() {
        //Given
        every { mealsRepository.getAllMeals() } returns emptyList()
        //when
        val result = getHealthyMealsFilterUseCase.getHealthyFastMeals()
        //then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return all meals if all are healthy and under 15 minutes`() {
        val meals = listOf(
        createMeal(name = "Salad", minutes = 10, totalFat = 1.0, saturatedFat = 0.4, carbohydrates = 3.5),
        createMeal(name = "Steamed Veggies", minutes = 12, totalFat = 1.0, saturatedFat = 0.4, carbohydrates = 3.5),
        createMeal(name = "Fruit Mix", minutes = 9, totalFat = 1.0, saturatedFat = 0.4, carbohydrates = 3.5)
        )

        //Given
        every { mealsRepository.getAllMeals() } returns meals
        //when
        val result = getHealthyMealsFilterUseCase.getHealthyFastMeals()
        //then
        assertThat(result).hasSize(3)
    }

    @Test
    fun `should return empty list if all meals are unhealthy`() {
        val meals = listOf(
            createMeal(name = "Pizza", minutes = 25, totalFat = 30.0, saturatedFat = 15.0, carbohydrates = 50.0),
            createMeal(name = "Donut", minutes = 20, totalFat = 25.0, saturatedFat = 12.0, carbohydrates = 45.0)
        )
        //Given
        every { mealsRepository.getAllMeals() } returns meals
        //when
        val result = getHealthyMealsFilterUseCase.getHealthyFastMeals()
        //then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should include meals equal to the threshold values`() {
        val meals = listOf(
            createMeal(name = "Exact Healthy", minutes = 15, totalFat = 8.0, saturatedFat = 4.0, carbohydrates = 20.0),
            createMeal(name = "Above Limit", minutes = 15, totalFat = 9.0, saturatedFat = 5.0, carbohydrates = 25.0)
        )
        //Given
        every { mealsRepository.getAllMeals() } returns meals
        //when
        val result = getHealthyMealsFilterUseCase.getHealthyFastMeals()
        //then
        assertThat(result.map { it.name }).contains("Exact Healthy")
        assertThat(result.map { it.name }).doesNotContain("Above Limit")
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