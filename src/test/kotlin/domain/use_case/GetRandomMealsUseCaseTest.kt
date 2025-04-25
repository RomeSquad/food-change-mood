package domain.use_case

import com.google.common.truth.Truth.assertThat
import data.meal.MealsRepository
import domain.use_case.FakeData.complexMealsFakeData
import domain.use_case.FakeData.easyMealsFakeData
import domain.use_case.FakeData.expectedMealNames
import domain.use_case.suggest.SuggestEasyFoodUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class GetRandomMealsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var suggestEasyFoodUseCase: SuggestEasyFoodUseCase


    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        suggestEasyFoodUseCase = SuggestEasyFoodUseCase(mealsRepository)
    }

    @Test
    fun `should return ten random meals  when the meals is easy to prepare`() {

        // Given
        every { mealsRepository.getAllMeals() } returns easyMealsFakeData

        // When
        val result = suggestEasyFoodUseCase.getEasyFoodSuggestion()

        // Then
        assertThat(result.map { it.name }).containsAnyIn(expectedMealNames)

    }

    @ParameterizedTest
    @ValueSource(ints = [1, 3, 5])
    fun `should return correct number of meals when the meals is easy to prepare`(numberOfMeals: Int) {

        // Given
        every { mealsRepository.getAllMeals() } returns easyMealsFakeData

        // When
        val result = suggestEasyFoodUseCase.getEasyFoodSuggestion(numberOfMeals)

        // Then
        assertThat(result).hasSize(numberOfMeals)


    }

    @Test
    fun `should throw NoSuchElementException when no easy meals are found`() {
        // Given
        every { mealsRepository.getAllMeals() } returns complexMealsFakeData

        // When // Then
        assertThrows<NoSuchElementException> {
            suggestEasyFoodUseCase.getEasyFoodSuggestion()
        }
    }


}

object FakeData {
    val easyMealsFakeData = listOf(
        createFakeMealData(
            mealName = "Chicken Curry",
            minutes = 2,
            stepsCount = 3,
            ingredientsCount = 4
        ),
        createFakeMealData(
            mealName = "Beef Stir Fry",
            minutes = 3,
            stepsCount = 2,
            ingredientsCount = 5
        ),
        createFakeMealData(
            mealName = "Vegetable Soup",
            minutes = 4,
            stepsCount = 1,
            ingredientsCount = 2
        ),
        createFakeMealData(
            mealName = "Pasta Salad",
            minutes = 1,
            stepsCount = 5,
            ingredientsCount = 3
        ),
        createFakeMealData(
            mealName = "Grilled Salmon",
            minutes = 5,
            stepsCount = 2,
            ingredientsCount = 1
        ),
        createFakeMealData(
            mealName = "Chicken Wrap",
            minutes = 2,
            stepsCount = 4,
            ingredientsCount = 5
        ),
        createFakeMealData(
            mealName = "Veggie Burger",
            minutes = 3,
            stepsCount = 3,
            ingredientsCount = 3
        ),
        createFakeMealData(
            mealName = "Scrambled Eggs",
            minutes = 1,
            stepsCount = 1,
            ingredientsCount = 1
        ),
        createFakeMealData(
            mealName = "Pork Chop",
            minutes = 4,
            stepsCount = 2,
            ingredientsCount = 4
        ),
        createFakeMealData(
            mealName = "Chicken Quesadilla",
            minutes = 2,
            stepsCount = 5,
            ingredientsCount = 2
        ),
        createFakeMealData(
            mealName = "Tofu Stir Fry",
            minutes = 5,
            stepsCount = 1,
            ingredientsCount = 5
        ),
        createFakeMealData(
            mealName = "Chicken Salad",
            minutes = 3,
            stepsCount = 2,
            ingredientsCount = 3
        ),
        createFakeMealData(
            mealName = "Complex Dish",
            minutes = 2,
            stepsCount = 10,
            ingredientsCount = 1
        )
    )

    val complexMealsFakeData = listOf(
        createFakeMealData(
            mealName = "Chicken Curry",
            minutes = 50,
            stepsCount = 3,
            ingredientsCount = 4
        ),
        createFakeMealData(
            mealName = "Beef Stir Fry",
            minutes = 70,
            stepsCount = 2,
            ingredientsCount = 5
        ),
        createFakeMealData(
            mealName = "Vegetable Soup",
            minutes = 80,
            stepsCount = 1,
            ingredientsCount = 2
        ),
        createFakeMealData(
            mealName = "Pasta Salad",
            minutes = 100,
            stepsCount = 5,
            ingredientsCount = 3
        ),
        createFakeMealData(
            mealName = "Grilled Salmon",
            minutes = 50,
            stepsCount = 2,
            ingredientsCount = 1
        ),
        createFakeMealData(
            mealName = "Chicken Wrap",
            minutes = 40,
            stepsCount = 4,
            ingredientsCount = 5
        ),
        createFakeMealData(
            mealName = "Veggie Burger",
            minutes = 37,
            stepsCount = 3,
            ingredientsCount = 3
        ),
        createFakeMealData(
            mealName = "Scrambled Eggs",
            minutes = 100,
            stepsCount = 1,
            ingredientsCount = 1
        ),
        createFakeMealData(
            mealName = "Pork Chop",
            minutes = 40,
            stepsCount = 2,
            ingredientsCount = 4
        ),
        createFakeMealData(
            mealName = "Chicken Quesadilla",
            minutes = 39,
            stepsCount = 5,
            ingredientsCount = 2
        ),
        createFakeMealData(
            mealName = "Tofu Stir Fry",
            minutes = 50,
            stepsCount = 1,
            ingredientsCount = 5
        ),
        createFakeMealData(
            mealName = "Chicken Salad",
            minutes = 38,
            stepsCount = 2,
            ingredientsCount = 3
        )
    )

    val expectedMealNames = listOf(
        "Chicken Curry", "Beef Stir Fry", "Vegetable Soup", "Pasta Salad",
        "Grilled Salmon", "Chicken Wrap", "Veggie Burger", "Scrambled Eggs",
        "Pork Chop", "Chicken Quesadilla", "Tofu Stir Fry", "Chicken Salad"
    )
}


