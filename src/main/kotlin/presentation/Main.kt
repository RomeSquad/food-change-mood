import data.CsvMealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParserImpl
import logic.GetMealsContainsCaloriesProteinUseCase
import logic.IdentifyIraqiMealsUseCase
import logic.use_case.GetLimitRandomMealsIncludePotatoesUseCase
import logic.use_case.GetTenRandomEasyMealsUseCase
import presentation.App
import java.io.File
import java.io.FileReader

fun main (){
    val file = File("food.csv")
    val fileReader = CsvFileReader(file)

    val csvParser = CsvParserImpl()
    val mealsRepository = CsvMealsRepository(fileReader,csvParser)
    // test your code here

    val startApp = App(
        mealsRepository,
        GetLimitRandomMealsIncludePotatoesUseCase(mealsRepository),
        GetTenRandomEasyMealsUseCase(mealsRepository),
        IdentifyIraqiMealsUseCase(mealsRepository),
        GetMealsContainsCaloriesProteinUseCase(mealsRepository)
    )
    startApp.start()
}