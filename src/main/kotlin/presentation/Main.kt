import data.CsvMealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParserImpl
import logic.use_case.IdentifyIraqiMealsUseCase
import java.io.File

fun main (){
    val file = File("food.csv")
    val fileReader = CsvFileReader(file)

    val csvParser = CsvParserImpl()
    val mealsRepository = CsvMealsRepository(fileReader,csvParser)
    mealsRepository.getAllMeals().let { println(it.size) }
    // test your code here
    val identifyIraqiMealsUseCase = IdentifyIraqiMealsUseCase(mealsRepository)
    identifyIraqiMealsUseCase.identifyIraqiMeals().forEach {
        println(it)
    }
}