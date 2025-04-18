package di

import domain.use_case.*
import logic.use_case.GetKetoDietMealsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { HealthyMealsFilterUseCase(get()) }                         /* 1 */
    single { GetByNameUseCase(get(), get()) }             /* 2 */
    single { GetIraqiMealsUseCase(get()) }                              /* 3 */
    single { GetRandomMealsUseCase(get()) }                             /* 4 */
    single { GuessGameUseCase(get()) }                                  /* 5 */
    single { GetSweetsWithoutEggsUseCase(get()) }                        /* 6 */
    single { GetKetoDietMealsUseCase(get()) }                           /* 7 */
    single { GetByDateUseCase(get()) }                                  /* 8 */
    single { GetMealsContainsCaloriesProteinUseCase(get()) }            /* 9 */
    single { GetMealsByCountryUseCase(get()) }                          /* 10 */
                                                                                        /* 11 */
    single { GetLimitRandomMealsIncludePotatoesUseCase(get()) }         /* 12 */
    single { GetMealsContainsHighCaloriesUseCase(get()) }               /* 13 */
    single { GetRankedSeafoodByProteinUseCase(get()) }                  /* 14 */
    single { GetItalianMealsForLargeGroupsUseCase(get()) }              /* 15 */
}