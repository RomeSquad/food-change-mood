package domain.use_case

import data.meal.MealsRepository
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach

class GetIngredientGameUseCaseTest {
    private lateinit var gameUseCase: GetIngredientGameUseCase
    private lateinit var mealsRepository: MealsRepository

    @BeforeEach
    fun steup() {
        mealsRepository = mockk(relaxed = true)
        gameUseCase = GetIngredientGameUseCase(mealsRepository)
    }
}