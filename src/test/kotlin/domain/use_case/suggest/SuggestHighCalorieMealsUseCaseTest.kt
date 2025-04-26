package domain.use_case.suggest

import com.google.common.truth.Truth.assertThat
import data.meal.MealsRepository
import data.model.Meal
import domain.NoMealsFoundException
import domain.use_case.createFakeMealData
import domain.use_case.createFakeNutrition
import domain.use_case.search.SearchMealsByDateUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SuggestHighCalorieMealsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var suggestHighCalorieMealsUseCase: SuggestHighCalorieMealsUseCase

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun `getNextMeal should return a high calorie meal`() {
        // Given
        mealsRepository = mockk()
        every { mealsRepository.getAllMeals() } returns getHighCalorieMeals(700.0)
        suggestHighCalorieMealsUseCase = SuggestHighCalorieMealsUseCase(mealsRepository)

        // When
        val meals = suggestHighCalorieMealsUseCase.getNextMeal()

        // Then
        assertThat(meals.nutrition.calories).isGreaterThan(700.0)
    }

    @Test
    fun `getNextMeal should return throw IllegalArgumentException for no high calorie meal`() {
        // Given
        mealsRepository = mockk()
        every { mealsRepository.getAllMeals() } returns getLowCalorieMeals(700.0)


        // When & Then
        assertThrows<IllegalArgumentException> {
            suggestHighCalorieMealsUseCase = SuggestHighCalorieMealsUseCase(mealsRepository)
        }
    }

    @Test
    fun `getNextMeal should return throw NoSuchElementException for no more meal`() {
        // Given
        mealsRepository = mockk()
        every { mealsRepository.getAllMeals() } returns getHighCalorieMeals(700.0)
        suggestHighCalorieMealsUseCase = SuggestHighCalorieMealsUseCase(mealsRepository)

        // When
        suggestHighCalorieMealsUseCase.getNextMeal()
        suggestHighCalorieMealsUseCase.getNextMeal()

        // Then
        assertThrows<NoSuchElementException> {
            suggestHighCalorieMealsUseCase.getNextMeal()
        }
    }

    fun getHighCalorieMeals(minCalories: Double): List<Meal> {
        return mutableListOf(
            createFakeMealData(
                nutrition = createFakeNutrition(calories = minCalories + 5)
            ),
            createFakeMealData(
                nutrition = createFakeNutrition(calories = minCalories + 10)
            ),
        )
    }

    fun getLowCalorieMeals(minCalories: Double): List<Meal> {
        return mutableListOf(
            createFakeMealData(
                nutrition = createFakeNutrition(calories = minCalories - 5)
            ),
            createFakeMealData(
                nutrition = createFakeNutrition(calories = minCalories - 10)
            ),
        )
    }

}