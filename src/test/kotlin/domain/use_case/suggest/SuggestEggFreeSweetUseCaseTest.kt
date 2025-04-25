package domain.use_case.suggest

import data.meal.MealsRepository
import data.model.Meal
import domain.use_case.createFakeMealData
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class SuggestEggFreeSweetUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var suggestEggFreeSweetUseCase: SuggestEggFreeSweetUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk()
        suggestEggFreeSweetUseCase = SuggestEggFreeSweetUseCase(mealsRepository)
    }


    @Test
    fun `suggestRandomSweet should return list of egg free sweets when tagged by sweet ingredient and not contain egg`() {
        val sweetsWithoutEgg = listOf(
            createFakeMealData(tags = listOf("sweet"), ingredients = listOf()),
        )
        every { mealsRepository.getAllMeals() } returns sweetsWithoutEgg

        val result = suggestEggFreeSweetUseCase.suggestRandomSweet()

        assertEquals(sweetsWithoutEgg.first(), result)

    }

    @Test
    fun `suggestRandomSweet should throw NoSuchElementException when tagged not contain sweet`() {
        val meals = listOf(
            createFakeMealData(tags = listOf("")),
        )
        every { mealsRepository.getAllMeals() } returns meals

        assertThrows<NoSuchElementException> {
            suggestEggFreeSweetUseCase.suggestRandomSweet()
        }


    }

    @Test
    fun `suggestRandomSweet should throw NoSuchElementException when sweet ingredients contain egg`() {
        val sweetWithEggs = listOf(
            createFakeMealData(tags = listOf("sweet"), ingredients = listOf("egg")),
        )
        every { mealsRepository.getAllMeals() } returns sweetWithEggs

        assertThrows<NoSuchElementException> {
            suggestEggFreeSweetUseCase.suggestRandomSweet()
        }

    }

    @Test
    fun `suggestRandomSweet should throw NoSuchElementException when no meals`() {
        val meals = emptyList<Meal>()
        every { mealsRepository.getAllMeals() } returns meals

        assertThrows<NoSuchElementException> {
            suggestEggFreeSweetUseCase.suggestRandomSweet()
        }

    }

}