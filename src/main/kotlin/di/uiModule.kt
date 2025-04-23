package di

import domain.use_case.search.SearchMealsByNameUseCase
import domain.use_case.fetch.GetQuickHealthyMealsUseCase
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.*
import presentation.input_output.ConsoleInputReader
import presentation.input_output.ConsoleUi
import presentation.input_output.InputReader
import presentation.input_output.UiExecutor

val uiModule = module {
    single { HealthyFastFoodAction(get()) }
    single { ConsoleUi() } bind UiExecutor::class
    single { ConsoleInputReader() } bind InputReader::class
    single { Menu(
        listOf(
            HealthyFastFoodAction(GetQuickHealthyMealsUseCase(get())),
            MealByNameAction(SearchMealsByNameUseCase(get(),get()))
        )
    ) }
}
