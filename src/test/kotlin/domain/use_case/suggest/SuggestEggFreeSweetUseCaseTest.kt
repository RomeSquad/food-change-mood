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
    fun `should return list of egg free sweet when tagged by sweet ingredient not contain egg`() {
        val sweetsWithoutEgg = listOf(
            createFakeMealData(tags = listOf("sweet"), ingredients = listOf()),
        )
        every { mealsRepository.getAllMeals() } returns sweetsWithoutEgg

        val result = suggestEggFreeSweetUseCase.suggestRandomSweet()

        assertEquals(sweetsWithoutEgg.first(), result)

    }

    @Test
    fun `should throw when tagged not contain sweet`() {
        val meals = listOf(
            createFakeMealData(tags = listOf("")),
        )
        every { mealsRepository.getAllMeals() } returns meals

        assertThrows<NoSuchElementException> {
            suggestEggFreeSweetUseCase.suggestRandomSweet()
        }


    }

    @Test
    fun `should throw when sweet ingredients contain egg`() {
        val sweetWithEggs = listOf(
            createFakeMealData(tags = listOf("sweet"), ingredients = listOf("egg")),
        )
        every { mealsRepository.getAllMeals() } returns sweetWithEggs

        assertThrows<NoSuchElementException> {
            suggestEggFreeSweetUseCase.suggestRandomSweet()
        }

    }

    @Test
    fun `should throw when no meals`() {
        val meals = emptyList<Meal>()
        every { mealsRepository.getAllMeals() } returns meals

        assertThrows<NoSuchElementException> {
            suggestEggFreeSweetUseCase.suggestRandomSweet()
        }

    }

}