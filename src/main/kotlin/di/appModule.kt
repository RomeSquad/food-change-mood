package di

import data.CsvMealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParserImpl
import logic.MealsRepository
import logic.use_case.BaseUseCases
import org.koin.dsl.module
import presentation.App
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single { CsvFileReader(get()) }
    single { CsvParserImpl() }

    single<MealsRepository> { CsvMealsRepository(get(), get()) }
    single { App(get()) }

    includes(useCaseModule)
}