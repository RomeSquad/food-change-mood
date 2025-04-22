package domain.use_case

import com.google.common.truth.Truth.assertThat
import data.meal.MealsRepository
import io.mockk.every
import io.mockk.mockk
import model.Meal
import model.Nutrition
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.lang.Exception
import java.text.SimpleDateFormat

class GetByDateUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var getByDateUseCase: GetByDateUseCase


    @BeforeEach
    fun setUp() {
        // Initialize the MealsRepository mock or stub here
        mealsRepository = mockk()
        getByDateUseCase = GetByDateUseCase(mealsRepository)

    }


    @Test
    fun `getByDate should return meals for valid date`() {
        // Given
        every { mealsRepository.getAllMeals() } returns getMealList()
        val date = "1999-7-02"

        // When
        val result = getByDateUseCase.getByDate(date)

        // Then
        assertThat(result).isEqualTo(listOf(getMealList()[1]))
    }

    @ParameterizedTest
    @CsvSource(
        "1999-7-",
        "1999-7-32",
        "1999-7-0",
        "1999-0-02",
        "1999-13-02",
        "1999--2",
        "1999-111-01",
        "1999-0-02",
        "999-7-02",
        "2029-7-02",
        "",
    )
    fun `getByDate should throw Exception for invalid date format`(date: String) {
        // Given
        every { mealsRepository.getAllMeals() } returns getMealList()

        // When && Then
        assertThrows<Exception> {
            getByDateUseCase.getByDate(date)
        }
    }

    @Test
    fun `getByDate should return empty list for no meals on the date`() {
        // Given
        every { mealsRepository.getAllMeals() } returns getMealList()
        val date = "2020-11-01"

        // When
        val result = getByDateUseCase.getByDate(date)

        // Then
        assertThat(result.isEmpty()).isTrue()
    }


    fun getMealList(): List<Meal> {
        return listOf(
            Meal(
                "Pasta",
                1,
                30,
                1,
                SimpleDateFormat(Meal.DATE_FORMAT).parse("2023-10-01"),
                listOf("Italian"),
                Nutrition(500.0, 20.0, 10.0, 5.0, 3.0, 1.0, 1.0),
                3,
                listOf("Boil water", "Add pasta", "Cook"),
                "Delicious pasta",
                listOf("Pasta", "Water"),
                2
            ),
            Meal(
                "Salad",
                2,
                15,
                2,
                SimpleDateFormat(Meal.DATE_FORMAT).parse("1999-7-02"),
                listOf("Healthy"),
                Nutrition(200.0, 5.0, 2.0, 3.0, 1.0, 1.0, 2.0),
                2,
                listOf("Chop vegetables", "Mix"),
                "Fresh salad",
                listOf("Lettuce", "Tomato"),
                2
            )
        )
    }
}