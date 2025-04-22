package domain.use_case

import data.meal.MealsRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMealsByCountryUseCaseTest{
  private lateinit var getMealsByCountryUseCase: GetMealsByCountryUseCase
  private val mealsRepository :MealsRepository = mockk<MealsRepository>(relaxed = true)

  @BeforeEach
  fun setup(){
      val dummyMealsByCountry : GetMealsByCountryUseCase = mockk()
      val  getMealsByCountryUseCase : GetMealsByCountryUseCase(mealsRepository)
  }

 @Test
 fun ` should return the name of the country if there is an interactions happend   `(){

  //When
   val checker:  getMealsByCountryUseCase.checkCountriesInName()
  //Then
  verify (exactly = 1){ }
 }


 }