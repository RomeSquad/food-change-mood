package domain.use_case.game

import data.meal.MealsRepository
import data.model.Meal
import data.model.Nutrition
import domain.use_case.search.SearchMealsByNameUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class GuessPreparationTimeGameUseCaseTest {

  private lateinit var repository: MealsRepository
  private lateinit var useCase: GuessPreparationTimeGameUseCase

 private val sampleMeals = listOf(
  Meal(
   id = 1,
   name = "Spaghetti",
   minutes = 20,
   contributorId = 101,
   submitted = Date(),
   tags = listOf("pasta"),
   nutrition = Nutrition(450.0, 15.0, 10.0, 500.0, 25.0, 5.0, 40.0),
   stepsCount = 3,
   steps = listOf("Boil water", "Cook pasta", "Add sauce"),
   description = "Easy pasta dish",
   ingredients = listOf("pasta", "sauce"),
   ingredientsCount = 2
  )
 )

@BeforeEach
 fun setUp() {
 repository = mockk()
 useCase = GuessPreparationTimeGameUseCase(repository)
 }

 @Test
 fun `should return a random meal when meals have valid preparation time`() {
  every { repository.getAllMeals() } returns sampleMeals

  val result = useCase.getRandomMealWithPreparationTime()

  assertNotNull(result)
  assertTrue(result!!.minutes > 0)
 }

 @Test
 fun `should return CORRECT when user guess matches actual preparation time`() {
  val result = useCase.checkUserGuess(20, 20)

  assertEquals(GuessPreparationTimeGameUseCase.GuessResult.CORRECT, result)
 }

 @Test
 fun `should return TOO_LOW when user guess is less than actual preparation time`() {
  val result = useCase.checkUserGuess(10, 20)

  assertEquals(GuessPreparationTimeGameUseCase.GuessResult.TOO_LOW, result)
 }

 @Test
 fun `should return TOO_HIGH when user guess is greater than actual preparation time`() {
  val result = useCase.checkUserGuess(30, 20)

  assertEquals(GuessPreparationTimeGameUseCase.GuessResult.TOO_HIGH, result)
 }
}