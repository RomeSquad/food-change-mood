package di

import data.meal.CsvMealsRepository
import data.meal.MealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParser
import data.utils.CsvParserImpl
import domain.use_case.search.utils.KMPSearchAlgorithm
import domain.use_case.search.utils.SearchAlgorithm
import org.koin.dsl.module
import presentation.App
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single { CsvFileReader(get()) }
    single<CsvParser> { CsvParserImpl() }

    single<MealsRepository> { CsvMealsRepository(get(), get()) }
    single {
        App(
            get(),
            get(),
            get(),
        )
    }
    single<SearchAlgorithm> { KMPSearchAlgorithm() }
}