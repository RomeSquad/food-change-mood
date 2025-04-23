package di

import domain.use_case.*
import logic.use_case.GetKetoDietMealsUseCase
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
            MealByNameAction(GetByNameUseCase(get(),get())),
            IraqiMealsAction(GetIraqiMealsUseCase(get())),
            EasyFoodSuggestionAction(GetRandomMealsUseCase(get())),
            PreparationTimeGuessingAction(GuessGameUseCase(get())),
            EggFreeSweetsAction(GetSweetsWithoutEggsUseCase(get())),
            KetoDietAction(GetKetoDietMealsUseCase(get())),
            MealByDateAction(GetByDateUseCase(get())),
            MealsByCaloriesAndProteinAction(GymHelperUseCase(get())),
            MealByCountryAction(GetMealsByCountryUseCase(get())),
            IngredientGameAction(GetIngredientGameUseCase(get())),
            PotatoMealsAction(GetMealsContainsPotatoUseCase(get())),
            HighCaloriesAction(GetMealsContainsHighCaloriesUseCase(get())),
            SeafoodMealsAction(GetSeafoodMealsUseCase(get())),
            ItalianMealForGroupsAction(GetItalianMealsForLargeGroupsUseCase(get()))
        )
    ) }
}
