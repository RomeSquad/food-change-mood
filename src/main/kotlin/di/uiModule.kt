package di

import domain.use_case.fetch.GetIraqiMealsUseCase
import domain.use_case.fetch.GetMealsContainsPotatoUseCase
import domain.use_case.fetch.GetQuickHealthyMealsUseCase
import domain.use_case.fetch.GetSeafoodMealsUseCase
import domain.use_case.game.GuessPreparationTimeGameUseCase
import domain.use_case.game.IngredientGameUseCase
import domain.use_case.search.SearchFoodByCountryUseCase
import domain.use_case.search.SearchGymHelperMealsUseCase
import domain.use_case.search.SearchMealsByDateUseCase
import domain.use_case.search.SearchMealsByNameUseCase
import domain.use_case.suggest.*
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
            MealByNameAction(SearchMealsByNameUseCase(get(), get())),
            IraqiMealsAction(GetIraqiMealsUseCase(get())),
            EasyFoodSuggestionAction(SuggestEasyFoodUseCase(get())),
            PreparationTimeGuessingAction(GuessPreparationTimeGameUseCase(get())),
            EggFreeSweetsAction(SuggestEggFreeSweetUseCase(get())),
            KetoDietAction(SuggestKetoMealUseCase(get())),
            MealByDateAction(SearchMealsByDateUseCase(get())),
            MealsByCaloriesAndProteinAction(SearchGymHelperMealsUseCase(get())),
            MealByCountryAction(SearchFoodByCountryUseCase(get())),
            IngredientGameAction(IngredientGameUseCase(get())),
            PotatoMealsAction(GetMealsContainsPotatoUseCase(get())),
            HighCaloriesAction(SuggestHighCalorieMealsUseCase(get())),
            SeafoodMealsAction(GetSeafoodMealsUseCase(get())),
            ItalianMealForGroupsAction(SuggestItalianFoodForGroupUseCase(get()))
        )
    ) }
}
