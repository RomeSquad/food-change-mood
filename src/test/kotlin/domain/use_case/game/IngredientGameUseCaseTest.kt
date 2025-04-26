package domain.use_case.game

import com.google.common.truth.Truth
import data.meal.MealsRepository
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IngredientGameUseCaseTest {
    private lateinit var gameUseCase: IngredientGameUseCase
    private lateinit var mealsRepository: MealsRepository

    @BeforeEach
    fun steup() {
        mealsRepository = mockk(relaxed = true)
        gameUseCase = IngredientGameUseCase(mealsRepository)
    }
    @Test
    fun `submitAnswer returns True And Increases Score when Answer Is Correct`() {
        // Given
        val correct = "Cheese"
        val selected = "Cheese"
        val initialScore = gameUseCase.getScore()

        // When
        val result = gameUseCase.submitAnswer(selected, correct)

        // Then
        Truth.assertThat(result).isTrue()
        Truth.assertThat(gameUseCase.getScore()).isEqualTo(initialScore + 1000)
    }

    @Test
    fun `submit Answer return False And Keep Score when Answer Is Wrong`() {
        // Given
        val correct = "Tomato"
        val selected = "Beef"
        val initialScore = gameUseCase.getScore()

        // When
        val result = gameUseCase.submitAnswer(selected, correct)

        // Then
        Truth.assertThat(result).isFalse()
        Truth.assertThat(gameUseCase.getScore()).isEqualTo(initialScore)
    }
}