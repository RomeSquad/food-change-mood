import data.CsvMealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParserImpl
import logic.use_case.SearchByNameUseCase
import logic.utils.SimpleSearchAlgorithm
import java.io.File

fun main (){
    val file = File("food.csv")
    val fileReader = CsvFileReader(file)

    val csvParser = CsvParserImpl()
    val mealsRepository = CsvMealsRepository(fileReader,csvParser)

    // test your code here
    val searchAlgorithm = SimpleSearchAlgorithm()
    val searchUseCase = SearchByNameUseCase(mealsRepository, searchAlgorithm)

    searchUseCase("Pizza").onSuccess {
        it.forEach { meal ->
            println(meal.name)
        }
    }.onFailure {
        println(it)
    }
}