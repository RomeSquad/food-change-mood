package domain.use_case

import com.google.common.truth.Truth.assertThat
import data.meal.MealsRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class GetRandomMealsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var getRandomMealsUseCase: GetRandomMealsUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        getRandomMealsUseCase = GetRandomMealsUseCase(mealsRepository)
    }

    @Test
    fun `should return one of ten random meals names when the meals is easy to prepare`() {

        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createFakeMealDataForRandomEasyMeals(mealName = "Chicken Curry", minutes = 2, nSteps = 3, nIngredients = 4),
            createFakeMealDataForRandomEasyMeals(mealName = "Beef Stir Fry", minutes = 3, nSteps = 2, nIngredients = 5),
            createFakeMealDataForRandomEasyMeals(mealName = "Vegetable Soup", minutes = 4, nSteps = 1, nIngredients = 2),
            createFakeMealDataForRandomEasyMeals(mealName = "Pasta Salad", minutes = 1, nSteps = 5, nIngredients = 3),
            createFakeMealDataForRandomEasyMeals(mealName = "Grilled Salmon", minutes = 5, nSteps = 2, nIngredients = 1),
            createFakeMealDataForRandomEasyMeals(mealName = "Chicken Wrap", minutes = 2, nSteps = 4, nIngredients = 5),
            createFakeMealDataForRandomEasyMeals(mealName = "Veggie Burger", minutes = 3, nSteps = 3, nIngredients = 3),
            createFakeMealDataForRandomEasyMeals(mealName = "Scrambled Eggs", minutes = 1, nSteps = 1, nIngredients = 1),
            createFakeMealDataForRandomEasyMeals(mealName = "Pork Chop", minutes = 4, nSteps = 2, nIngredients = 4),
            createFakeMealDataForRandomEasyMeals(mealName = "Chicken Quesadilla", minutes = 2, nSteps = 5, nIngredients = 2),
            createFakeMealDataForRandomEasyMeals(mealName = "Tofu Stir Fry", minutes = 5, nSteps = 1, nIngredients = 5),
            createFakeMealDataForRandomEasyMeals(mealName = "Chicken Salad", minutes = 3, nSteps = 2, nIngredients = 3),

            createFakeMealDataForRandomEasyMeals(mealName = "Complex Dish", minutes = 2, nSteps = 10, nIngredients = 1)
        )

        // When
        val result = getRandomMealsUseCase.getTenRandomEasyMeals()

        // Then
        val expectedMealNames = listOf(
            "Chicken Curry", "Beef Stir Fry", "Vegetable Soup", "Pasta Salad",
            "Grilled Salmon", "Chicken Wrap", "Veggie Burger", "Scrambled Eggs",
            "Pork Chop", "Chicken Quesadilla", "Tofu Stir Fry", "Chicken Salad"
        )
        assertThat(result.map { it.name }).containsAnyIn(expectedMealNames)

    }

    @ParameterizedTest
    @ValueSource(ints = [1, 3, 5])
    fun `should return correct number of meals when the meals is easy to prepare`(numberOfMeals: Int) {

        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createFakeMealDataForRandomEasyMeals(mealName = "Chicken Curry", minutes = 2, nSteps = 3, nIngredients = 4),
            createFakeMealDataForRandomEasyMeals(mealName = "Beef Stir Fry", minutes = 3, nSteps = 2, nIngredients = 5),
            createFakeMealDataForRandomEasyMeals(mealName = "Vegetable Soup", minutes = 4, nSteps = 1, nIngredients = 2),
            createFakeMealDataForRandomEasyMeals(mealName = "Pasta Salad", minutes = 1, nSteps = 5, nIngredients = 3),
            createFakeMealDataForRandomEasyMeals(mealName = "Grilled Salmon", minutes = 5, nSteps = 2, nIngredients = 1),
            createFakeMealDataForRandomEasyMeals(mealName = "Chicken Wrap", minutes = 2, nSteps = 4, nIngredients = 5),
            createFakeMealDataForRandomEasyMeals(mealName = "Veggie Burger", minutes = 3, nSteps = 3, nIngredients = 3),
            createFakeMealDataForRandomEasyMeals(mealName = "Scrambled Eggs", minutes = 1, nSteps = 1, nIngredients = 1),
            createFakeMealDataForRandomEasyMeals(mealName = "Pork Chop", minutes = 4, nSteps = 2, nIngredients = 4),
            createFakeMealDataForRandomEasyMeals(mealName = "Chicken Quesadilla", minutes = 2, nSteps = 5, nIngredients = 2),
            createFakeMealDataForRandomEasyMeals(mealName = "Tofu Stir Fry", minutes = 5, nSteps = 1, nIngredients = 5),
            createFakeMealDataForRandomEasyMeals(mealName = "Chicken Salad", minutes = 3, nSteps = 2, nIngredients = 3),

            createFakeMealDataForRandomEasyMeals(mealName = "Complex Dish", minutes = 2, nSteps = 10, nIngredients = 1)
        )
        // When
        val result = getRandomMealsUseCase.getNRandomEasyMeals(numberOfMeals)

        // Then
        assertThat(result).hasSize(numberOfMeals)


    }

}

