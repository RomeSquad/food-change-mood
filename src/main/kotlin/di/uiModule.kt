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
import presentation.action.fetch.HealthyFastFoodAction
import presentation.action.fetch.IraqiMealsAction
import presentation.action.fetch.PotatoMealsAction
import presentation.action.fetch.SeafoodMealsAction
import presentation.action.game.IngredientGameAction
import presentation.action.game.PreparationTimeGuessingAction
import presentation.action.search.MealByCountryAction
import presentation.action.search.MealByDateAction
import presentation.action.search.MealByNameAction
import presentation.action.search.MealsByCaloriesAndProteinAction
import presentation.action.suggest.EasyFoodSuggestionAction
import presentation.action.suggest.EggFreeSweetsAction
import presentation.action.suggest.HighCaloriesAction
import presentation.action.suggest.ItalianMealForGroupsAction
import presentation.action.suggest.KetoDietAction
import presentation.io.ConsoleInputReader
import presentation.io.ConsoleUi
import presentation.io.InputReader
import presentation.io.UiExecutor

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
