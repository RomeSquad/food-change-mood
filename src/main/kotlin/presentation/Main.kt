package presentation

import data.CsvMealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParserImpl
import di.appModule
import di.useCaseModule
import logic.use_case.BaseUseCases
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import presentation.App
import java.io.File

fun main () {
    startKoin {
        modules(appModule)
    }
    val app: App = getKoin().get()
    app.start()
}
//    val file = File("food.csv")
//    val fileReader = CsvFileReader(file)
//
//    val csvParser = CsvParserImpl()
//    val mealsRepository = CsvMealsRepository(fileReader,csvParser)
//
//    val startApp = App(
//        mealsRepository,
//    )
//    startApp.start()
// }