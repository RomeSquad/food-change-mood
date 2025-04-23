package di

import domain.use_case.GetByNameUseCase
import domain.use_case.GetHealthyMealsFilterUseCase
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
            HealthyFastFoodAction(GetHealthyMealsFilterUseCase(get())),
            MealByNameAction(GetByNameUseCase(get(),get()))
        )
    ) }
}
