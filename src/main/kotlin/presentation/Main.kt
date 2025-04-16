import data.CsvMealsRepository
import data.utils.CsvParserImpl
import logic.use_case.IdentifyIraqiMealsUseCase
import java.io.File

fun main (){
    val file = File("food.csv")

    val csvParser = CsvParserImpl()
    val mealsRepository = CsvMealsRepository(file,csvParser)

    val identifyIraqiMealsUseCase = IdentifyIraqiMealsUseCase(mealsRepository)
    identifyIraqiMealsUseCase.identifyIraqiMeals().forEach {
        println(it)
    }
}