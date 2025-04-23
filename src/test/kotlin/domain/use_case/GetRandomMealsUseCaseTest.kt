package domain.use_case

import com.google.common.truth.Truth.assertThat
import data.meal.MealsRepository
import io.mockk.every
import io.mockk.mockk
import model.Meal
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*


class GetRandomMealsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var getRandomMealsUseCase: GetRandomMealsUseCase

    private fun createFakeMealDataForRandomEasyMeals (
        mealName : String,
        minutes : Int,
        nSteps : Int,
        nIngredients :Int
    )=  Meal(
        name = mealName,
        minutes = minutes,
        nSteps = nSteps,
        nIngredients = nIngredients,
        contributorId = 47892,
        submitted = Date("9/16/2005"),
        tags = listOf(
            "30-minutes-or-less",
            "time-to-make",
            "course",
            "main-ingredient",
            "preparation",
            "italian",
            "easy"
        ),
        nutrition = Nutrition(
            calories = 450.0,
            totalFat = 18.0,
            sugar = 2.0,
            sodium = 650.0,
            protein = 22.0,
            saturatedFat = 8.0,
            carbohydrates = 45.0
        ),
        steps = listOf(
            "Bring large pot of salted water to boil",
            "Cook spaghetti according to package directions",
            "Whisk eggs and grated cheese in a bowl",
            "Cook pancetta until crispy, then add cooked pasta",
            "Quickly mix in egg mixture off heat to create creamy sauce"
        ),
        description = "Classic Roman pasta dish with eggs, cheese, pancetta, and pepper. The key is to mix the eggs quickly to create a creamy sauce without scrambling them.",
        ingredients = listOf(
            "spaghetti",
            "eggs",
            "pecorino cheese",
            "pancetta",
            "black pepper",
            "salt",
            "garlic"
        ),
        id = 137739
    )

    private val easyMealsFakeData = listOf(
        createFakeMealDataForRandomEasyMeals(mealName = "Chicken Curry", minutes = 2, nSteps = 3, nIngredients = 4),
        createFakeMealDataForRandomEasyMeals(mealName = "Beef Stir Fry", minutes = 3, nSteps = 2, nIngredients = 5),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Vegetable Soup",
            minutes = 4,
            nSteps = 1,
            nIngredients = 2
        ),
        createFakeMealDataForRandomEasyMeals(mealName = "Pasta Salad", minutes = 1, nSteps = 5, nIngredients = 3),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Grilled Salmon",
            minutes = 5,
            nSteps = 2,
            nIngredients = 1
        ),
        createFakeMealDataForRandomEasyMeals(mealName = "Chicken Wrap", minutes = 2, nSteps = 4, nIngredients = 5),
        createFakeMealDataForRandomEasyMeals(mealName = "Veggie Burger", minutes = 3, nSteps = 3, nIngredients = 3),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Scrambled Eggs",
            minutes = 1,
            nSteps = 1,
            nIngredients = 1
        ),
        createFakeMealDataForRandomEasyMeals(mealName = "Pork Chop", minutes = 4, nSteps = 2, nIngredients = 4),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Chicken Quesadilla",
            minutes = 2,
            nSteps = 5,
            nIngredients = 2
        ),
        createFakeMealDataForRandomEasyMeals(mealName = "Tofu Stir Fry", minutes = 5, nSteps = 1, nIngredients = 5),
        createFakeMealDataForRandomEasyMeals(mealName = "Chicken Salad", minutes = 3, nSteps = 2, nIngredients = 3),

        createFakeMealDataForRandomEasyMeals(mealName = "Complex Dish", minutes = 2, nSteps = 10, nIngredients = 1)
    )
    private val complexMealsFakeData = listOf(
        createFakeMealDataForRandomEasyMeals(
            mealName = "Chicken Curry",
            minutes = 50,
            nSteps = 3,
            nIngredients = 4
        ),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Beef Stir Fry",
            minutes = 70,
            nSteps = 2,
            nIngredients = 5
        ),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Vegetable Soup",
            minutes = 80,
            nSteps = 1,
            nIngredients = 2
        ),
        createFakeMealDataForRandomEasyMeals(mealName = "Pasta Salad", minutes = 100, nSteps = 5, nIngredients = 3),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Grilled Salmon",
            minutes = 50,
            nSteps = 2,
            nIngredients = 1
        ),
        createFakeMealDataForRandomEasyMeals(mealName = "Chicken Wrap", minutes = 40, nSteps = 4, nIngredients = 5),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Veggie Burger",
            minutes = 37,
            nSteps = 3,
            nIngredients = 3
        ),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Scrambled Eggs",
            minutes = 100,
            nSteps = 1,
            nIngredients = 1
        ),
        createFakeMealDataForRandomEasyMeals(mealName = "Pork Chop", minutes = 40, nSteps = 2, nIngredients = 4),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Chicken Quesadilla",
            minutes = 39,
            nSteps = 5,
            nIngredients = 2
        ),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Tofu Stir Fry",
            minutes = 50,
            nSteps = 1,
            nIngredients = 5
        ),
        createFakeMealDataForRandomEasyMeals(
            mealName = "Chicken Salad",
            minutes = 38,
            nSteps = 2,
            nIngredients = 3
        ),

        )

    private val expectedMealNames = listOf(
        "Chicken Curry", "Beef Stir Fry", "Vegetable Soup", "Pasta Salad",
        "Grilled Salmon", "Chicken Wrap", "Veggie Burger", "Scrambled Eggs",
        "Pork Chop", "Chicken Quesadilla", "Tofu Stir Fry", "Chicken Salad"
    )

    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        getRandomMealsUseCase = GetRandomMealsUseCase(mealsRepository)
    }

    @Test
    fun `should return one of ten random meals names when the meals is easy to prepare`() {

        // Given
        every { mealsRepository.getAllMeals() } returns easyMealsFakeData

        // When
        val result = getRandomMealsUseCase.getNRandomEasyMeals()

        // Then
        assertThat(result.map { it.name }).containsAnyIn(expectedMealNames)

    }

    @Test
    fun `should return empty list when the meals is not easy to prepare`() {

        // Given
        every { mealsRepository.getAllMeals() } returns complexMealsFakeData

        // When
        val result = getRandomMealsUseCase.getNRandomEasyMeals()

        // Then
        assertThat(result)

    }

    @ParameterizedTest
    @ValueSource(ints = [1, 3, 5])
    fun `should return correct number of meals when the meals is easy to prepare`(numberOfMeals: Int) {

        // Given
        every { mealsRepository.getAllMeals() } returns easyMealsFakeData

        // When
        val result = getRandomMealsUseCase.getNRandomEasyMeals(numberOfMeals)

        // Then
        assertThat(result).hasSize(numberOfMeals)


    }

}

