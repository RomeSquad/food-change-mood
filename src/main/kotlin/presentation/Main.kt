package presentation

import data.CsvMealsRepository
import data.utils.CsvParserImpl
import java.io.File

fun main (){
    val file = File("food.csv")

    val csvParser = CsvParserImpl()
    val mealsRepository = CsvMealsRepository(file,csvParser)
   // println(mealsRepository.getAllMeals().map { it.id }.joinToString(","))

    // Amr Nasser Task 4
    //val getTenRandomMealsEasyToPrepare = GetTenRandomEasyMealsUseCase(mealsRepository)
   // println(getTenRandomMealsEasyToPrepare.getTenRandomEasyMeals())
}