import data.CsvMealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParserImpl
import logic.IdentifyIraqiMealsUseCase
import java.io.File
import java.io.FileReader

fun main (){
    val file = File("food.csv")
    val fileReader = CsvFileReader(file)

    val csvParser = CsvParserImpl()
    val mealsRepository = CsvMealsRepository(fileReader,csvParser)
    //mealsRepository.getAllMeals().let { println(it.size) }
    // test your code here

    val startApp = App(
        mealsRepository,
    )
    startApp.start()
}