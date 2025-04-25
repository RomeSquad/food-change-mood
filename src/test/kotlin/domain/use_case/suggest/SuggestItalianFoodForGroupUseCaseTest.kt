package domain.use_case.suggest

import data.meal.MealsRepository
import domain.NoMealsFoundException
import domain.use_case.createMeal
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class SuggestItalianFoodForGroupUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var suggestItalianFoodForGroupUseCase: SuggestItalianFoodForGroupUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk()
        suggestItalianFoodForGroupUseCase = SuggestItalianFoodForGroupUseCase(mealsRepository)
    }

    @Test
    fun `should throw NoMealsFoundException when no meals found`() {
        every { mealsRepository.getAllMeals() } returns emptyList()

        assertThrows<NoMealsFoundException> {
            suggestItalianFoodForGroupUseCase.suggestItalianMealsForLargeGroup()
        }
    }

    @Test
    fun `should return list of italian meals when tagged by for-large-groups and description contain italian`() {
        val meals = listOf(
            createMeal(tag = listOf("for-large-groups",""), description = "italian"),
            createMeal(tag = listOf("", "for-large-groups"), description = "italian")
        )
        every { mealsRepository.getAllMeals() } returns meals

        val result = suggestItalianFoodForGroupUseCase.suggestItalianMealsForLargeGroup()

        assertEquals(meals, result)
    }

    @Test
    fun `should return list of italian meals when tagged by for-large-groups and tagged by italian`() {
        val meals = listOf(
            createMeal(tag = listOf("for-large-groups","italian"), description = ""),
            createMeal(tag = listOf("italian", "for-large-groups"), description = "")
        )
        every { mealsRepository.getAllMeals() } returns meals

        val result = suggestItalianFoodForGroupUseCase.suggestItalianMealsForLargeGroup()

        assertEquals(meals, result)
    }

    @Test
    fun `should return list of italian meals when tagged by for-large-groups and tagged or description contain italian`() {
        val meals = listOf(
            createMeal(tag = listOf("for-large-groups",""), description = "italian"),
            createMeal(tag = listOf("italian", "for-large-groups"), description = "")
        )
        every { mealsRepository.getAllMeals() } returns meals

        val result = suggestItalianFoodForGroupUseCase.suggestItalianMealsForLargeGroup()

        assertEquals(meals, result)
    }

    @Test
    fun `should return list of italian meals when ignore cases in tag and description`() {
        val meals = listOf(
            createMeal(tag = listOf("For-Large-Groups",""), description = "italian"),
            createMeal(tag = listOf("Italian", "for-large-groups"), description = ""),
            createMeal(tag = listOf("for-large-groups"), description = "Italian")
        )
        every { mealsRepository.getAllMeals() } returns meals

        val result = suggestItalianFoodForGroupUseCase.suggestItalianMealsForLargeGroup()

        assertEquals(meals, result)
    }

    @Test
    fun `should throw exception when meals not tagged by for-large-groups but description or tag contain italian`() {
        val meals = listOf(
            createMeal(tag = listOf("",""), description = "italian"),
            createMeal(tag = listOf("italian", ""), description = "")
        )
        every { mealsRepository.getAllMeals() } returns meals

        assertThrows<NoMealsFoundException> {
            suggestItalianFoodForGroupUseCase.suggestItalianMealsForLargeGroup()
        }
    }

    @Test
    fun `should return list of italian meals when tagged by for-large-groups and tag italian but description is null`() {
        val meals = listOf(
            createMeal(tag = listOf("for-large-groups", "italian"), description = null),
        )
        every { mealsRepository.getAllMeals() } returns meals

        val result = suggestItalianFoodForGroupUseCase.suggestItalianMealsForLargeGroup()

        assertEquals(meals, result)
    }

    @Test
    fun `should throw exception when tagged by for-large-groups but description is null`() {
        val meals = listOf(
            createMeal(tag = listOf("for-large-groups"), description = null),
        )
        every { mealsRepository.getAllMeals() } returns meals

        assertThrows<NoMealsFoundException> { suggestItalianFoodForGroupUseCase.suggestItalianMealsForLargeGroup() }
    }
}