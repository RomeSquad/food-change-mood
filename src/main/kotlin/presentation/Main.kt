import data.CsvMealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParserImpl
import logic.use_case.SearchByNameUseCase
import logic.utils.SimpleSearchAlgorithm
import java.io.File
import java.io.FileReader

fun main (){
    val file = File("food.csv")
    val fileReader = CsvFileReader(file)

    val csvParser = CsvParserImpl()
    val mealsRepository = CsvMealsRepository(fileReader,csvParser)
    val startApp = App(
        mealsRepository,
    )
    startApp.start()
}