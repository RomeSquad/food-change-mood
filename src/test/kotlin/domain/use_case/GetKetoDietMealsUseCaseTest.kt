package domain.use_case

import data.meal.MealsRepository
import io.mockk.every
import io.mockk.mockk
import logic.use_case.GetKetoDietMealsUseCase
import model.Meal
import model.Nutrition
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.util.*
import kotlin.test.Test

class GetKetoDietMealsUseCaseTest{

    private lateinit var  mealsRepository: MealsRepository
    private lateinit var getKetoDietMealsUseCase : GetKetoDietMealsUseCase

    @BeforeEach
    fun setup(){
        mealsRepository = mockk(relaxed = true)
        getKetoDietMealsUseCase = GetKetoDietMealsUseCase(mealsRepository)
    }

    @Test
    fun `should return a keto meal when it runs for the first time`(){
        //given
        every { mealsRepository.getAllMeals() } returns listOf(

            createKetoMeals(10983,Nutrition(276.0, 17.0, 1.0, 13.0, 69.0, 16.0, 1.0)),
            createKetoMeals(17768,Nutrition(301.4,29.0,13.0,61.0,57.0,57.0,0.0)),
            createKetoMeals(15679,Nutrition(480.4,45.0,19.0,61.0,26.0,57.0,9.0)),
            createKetoMeals(1560009,Nutrition(167.4,20.0,19.0,61.0,19.0,57.0,0.0))
        )


        // when
        val ketoMeal = getKetoDietMealsUseCase.getNextKetoMeal()

        // then
        assertTrue(ketoMeal.nutrition.carbohydrates < 10)
        assertTrue(ketoMeal.nutrition.totalFat >= 15)
        assertTrue(ketoMeal.nutrition.protein >= 10)
    }

    @Test
    fun `should return a different keto meal when it runs in a different time`() {
        //given
        every { mealsRepository.getAllMeals() } returns listOf(

            createKetoMeals(10983,Nutrition(276.0, 17.0, 1.0, 13.0, 69.0, 16.0, 1.0)),
            createKetoMeals(17768,Nutrition(301.4,29.0,13.0,61.0,57.0,57.0,0.0)),
            createKetoMeals(15679,Nutrition(480.4,45.0,19.0,61.0,26.0,57.0,9.0)),
            createKetoMeals(1560009,Nutrition(167.4,20.0,19.0,61.0,19.0,57.0,0.0))
        )

        // when
        val firstKetoMeal = getKetoDietMealsUseCase.getNextKetoMeal()
        val secondKetoMeals = getKetoDietMealsUseCase.getNextKetoMeal()

        // then
        assertNotEquals(firstKetoMeal.id, secondKetoMeals.id)

}


    fun createKetoMeals(ID: Int, nutrition: Nutrition) = Meal(
        name = "pollo alla toscana",
        minutes = 40,
        contributorId = 134,
        submitted = Date(),
        tags = listOf(
            "60-minutes-or-less", "time-to-make",
            "course", "main-ingredient",
            "cuisine", "preparation",
            "main-dish", "poultry", "european",
            "italian", "chicken", "meat"
        ),
        nSteps = 17,
        steps = listOf(
            "heat a large",
            " deep skillet over medium high heat",
            "season chicken with salt and pepper",
            "add 2 tablespoons extra-virgin olive oil",
            " half the chicken pieces  and a couple of crushed cloves of garlic",
            "brown chicken 2 minutes on each side and remove from pan",
            "add remaining olive oil",
            "another single turn of the pan",
            "the remaining chicken pieces and garlic",
            "brown chicken 2 minutes on each side and remove",
            "return chicken to the pan and simmer over moderate heat 7 to 8 minutes to finish cooking chicken through"
        ),
        description = null,
        ingredients = listOf(
            "boneless skinless chicken thighs",
            "chicken tenderloins",
            "salt and pepper",
            "extra virgin olive oil",
            "garlic cloves",
            "white wine vinegar",
            "butter",
            "shallots",
            "fresh rosemary",
            "flour",
            "dry white wine",
            "beef broth"
        ),
        nIngredients = 8, id = ID, nutrition = nutrition
    )

}