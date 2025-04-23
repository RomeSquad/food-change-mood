package di

import domain.use_case.fetch.GetIraqiMealsUseCase
import domain.use_case.fetch.GetMealsContainsPotatoUseCase
import domain.use_case.fetch.GetQuickHealthyMealsUseCase
import domain.use_case.fetch.GetSeafoodMealsUseCase
import domain.use_case.game.GuessPreparationTimeGameUseCase
import domain.use_case.game.IngredientGameUseCase
import domain.use_case.search.SearchFoodByCultureUseCase
import domain.use_case.search.SearchGymFriendlyMealsUseCase
import domain.use_case.search.SearchMealsByDateUseCase
import domain.use_case.search.SearchMealsByNameUseCase
import domain.use_case.suggest.*
import org.koin.dsl.module

val useCaseModule = module {
    single { GetQuickHealthyMealsUseCase(get()) }
    single { SearchMealsByNameUseCase(get(), get()) }
    single { GetIraqiMealsUseCase(get()) }
    single { SuggestEasyFoodUseCase(get()) }
    single { GuessPreparationTimeGameUseCase(get()) }
    single { SuggestEggFreeSweetUseCase(get()) }
    single { SuggestKetoMealUseCase(get()) }
    single { SearchMealsByDateUseCase(get()) }
    single { SearchGymFriendlyMealsUseCase(get()) }
    single { SearchFoodByCultureUseCase(get()) }
    single { IngredientGameUseCase(get()) }
    single { GetMealsContainsPotatoUseCase(get()) }
    single { SuggestHighCalorieMealsUseCase(get()) }
    single { GetSeafoodMealsUseCase(get()) }
    single { SuggestItalianFoodForGroupUseCase(get()) }
}