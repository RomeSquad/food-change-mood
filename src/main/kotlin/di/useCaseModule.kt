package di

import logic.use_case.*
import org.koin.dsl.module

val useCaseModule = module {
    // single { SearchByNameUseCase(get(), get()) }     // 2
    single { IdentifyIraqiMealsUseCase(get()) }      // 3
    // single { GetTenRandomEasyMealsUseCase(get()) }   // 4
    // single { GuessGameUseCase(get()) }               // 5
    // single { GetMealsContainsCaloriesProteinUseCase(get()) }     // 9
    // single { GetLimitRandomMealsIncludePotatoesUseCase(get()) }  // 12
}