package fake

import data.meal.MealsRepository
import model.Meal
import model.Nutrition
import java.util.*

class FakeMealsRepository :MealsRepository {

    private val CountryName: String = "iraq"

    override fun getAllMeals(): List<Meal> {
        return listOf(
            Meal(
                name = CountryName ,
                id = 3,
                minutes = 5,
                contributorId = 23,
                submitted = Date(22-4-2025),
                tags = listOf(CountryName,"vegetables") ,
                nutrition = Nutrition(0.0, 0.0, 0.0, 0.0, 0.0,0.0,0.0),
                nSteps = 4,
                steps = listOf(CountryName,"makes about 2 cups" ),
                description = CountryName,
                ingredients = listOf(CountryName,"dolma") ,
                nIngredients = 6
            )
        )
    }
}