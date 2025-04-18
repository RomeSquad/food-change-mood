import data.meal.CsvMealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParserImpl
import logic.use_case.IngredientGameUseCase
import model.Meal
import presentation.App
import java.io.File

fun main() {
    val file = File("food.csv")
    val fileReader = CsvFileReader(file)

    val csvParser = CsvParserImpl()
    val mealsRepository = CsvMealsRepository(fileReader, csvParser)
    val startApp = App(mealsRepository)
    startApp.start()

}