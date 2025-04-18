package di

import domain.use_case.GetByDateUseCase
import domain.use_case.GetIraqiMealsUseCase
import domain.use_case.GetItalianMealsForLargeGroupsUseCase
import domain.use_case.GetLimitRandomMealsIncludePotatoesUseCase
import domain.use_case.GetMealsByCountryUseCase
import domain.use_case.GetMealsContainsCaloriesProteinUseCase
import domain.use_case.GetMealsContainsHighCaloriesUseCase
import domain.use_case.GetRandomMealsUseCase
import domain.use_case.GetRankedSeafoodByProteinUseCase
import domain.use_case.GetSweetsWithNoEggsUseCase
import domain.use_case.GuessGameUseCase
import domain.use_case.HealthyMealsFilterUseCase
import domain.use_case.SearchByNameUseCase
import logic.use_case.GetKetoDietMealsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { HealthyMealsFilterUseCase(get()) }                         /* 1 */
    single { SearchByNameUseCase(get(), get()) }             /* 2 */
    single { GetIraqiMealsUseCase(get()) }                              /* 3 */
    single { GetRandomMealsUseCase(get()) }                             /* 4 */
    single { GuessGameUseCase(get()) }                                  /* 5 */
    single { GetSweetsWithNoEggsUseCase(get()) }                        /* 6 */
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