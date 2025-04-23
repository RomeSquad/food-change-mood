package di

import domain.use_case.*
import logic.use_case.GetKetoDietMealsUseCase
import domain.use_case.GetIngredientGameUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetHealthyMealsFilterUseCase(get()) }
    single { GetByNameUseCase(get(), get()) }
    single { GetIraqiMealsUseCase(get()) }
    single { GetRandomMealsUseCase(get()) }
    single { GuessGameUseCase(get()) }
    single { GetSweetsWithoutEggsUseCase(get()) }
    single { GetKetoDietMealsUseCase(get()) }
    single { GetByDateUseCase(get()) }
    single { GymHelperUseCase(get()) }
    single { GetMealsByCountryUseCase(get()) }
    single { GetIngredientGameUseCase(get()) }
    single { GetMealsContainsPotatoUseCase(get()) }
    single { GetMealsContainsHighCaloriesUseCase(get()) }
    single { GetSeafoodMealsUseCase(get()) }
    single { GetItalianMealsForLargeGroupsUseCase(get()) }
}