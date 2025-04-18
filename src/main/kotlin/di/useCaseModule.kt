package di

import logic.use_case.*
import org.koin.dsl.module

val useCaseModule = module {
    single { HealthyMealsFilter() }                                             /* 1 */
    single { SearchByNameUseCase(get(), get()) }     /* 2 */
    single { IdentifyIraqiMealsUseCase(get()) }                 /* 3 */
    single { GetTenRandomEasyMealsUseCase(get()) }              /* 4 */
    single { GuessGameUseCase(get()) }                          /* 5 */
    single { GetSweetsWithNoEggsUseCase(get()) }                /* 6 */
    single { KetoDietHelper(get()) }                                    /* 7 */
    single { GetByDateUseCase(get()) }                          /* 8 */
    single { GetMealsContainsCaloriesProteinUseCase(get()) }    /* 9 */
    single { GetLimitRandomMealsIncludePotatoesUseCase(get()) } /* 12 */
    single { GetCaloriesMoreThanUseCase(get()) }                /* 13 */
    single { GetRankedSeafoodByProteinUseCase(get()) }          /* 14 */
    single { SuggestItalianMealsForLargeGroupsUseCase(get()) }  /* 15 */
}