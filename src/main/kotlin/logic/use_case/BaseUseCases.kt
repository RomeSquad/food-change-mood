package logic.use_case

data class BaseUseCases (
    val searchByNameUseCase: SearchByNameUseCase,
    val identifyIraqiMealsUseCase: IdentifyIraqiMealsUseCase,
    val getTenRandomEasyMealsUseCase: GetTenRandomEasyMealsUseCase,
    val guessGameUseCase: GuessGameUseCase,
    val getMealsContainsCaloriesProteinUseCase: GetMealsContainsCaloriesProteinUseCase,
    val getLimitRandomMealsIncludePotatoesUseCase: GetLimitRandomMealsIncludePotatoesUseCase
)
