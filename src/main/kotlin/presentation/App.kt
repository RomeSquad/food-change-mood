package presentation

import data.meal.CsvMealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParserImpl
import data.meal.MealsRepository
import domain.use_case.*
import domain.utils.SearchAlgorithmFactory
import logic.use_case.GetKetoDietMealsUseCase
import model.Meal
import java.io.File

class App(

    private val mealsRepository: CsvMealsRepository,
) {
    fun start() {
        while (true) {
            printMenu()
            val selectedAction = getSelectedAction()

            if (selectedAction == MenuItemUi.EXIT) break

            executeAction(selectedAction, mealsRepository)
        }
    }

    private fun printMenu() {
        MenuItemUi.entries.forEachIndexed { index, action ->
            println("${index + 1}- ${action.description}")
        }
        print(coloredPrompt("Choose the action *enter (8) or anything else to exit*: "))
    }

    private fun coloredPrompt(text: String): String {
        val yellow = "\u001B[33m"
        val reset = "\u001B[0m"
        return "$yellow$text$reset"
    }

    private fun getSelectedAction(): MenuItemUi {
        return readln().toIntOrNull()?.toMenuItem() ?: MenuItemUi.EXIT
    }


    private fun executeAction(selectedAction: MenuItemUi, mealsRepository: MealsRepository) {
        when (selectedAction) {
            MenuItemUi.HEALTHY_FAST_FOOD -> showHealthyFastFood(mealsRepository)
            MenuItemUi.MEAL_BY_NAME -> showMealByName()
            MenuItemUi.IRAQI_MEALS -> showIraqiMeals()
            MenuItemUi.EASY_FOOD_SUGGESTION_GAME -> showEasyFoodSuggestionGame()
            MenuItemUi.PREPARATION_TIME_GUESSING_GAME -> showPreparationTimeGuessingGame()
            MenuItemUi.EGG_FREE_SWEETS -> showEggFreeSweets()
            MenuItemUi.KETO_DIET_MEAL -> showKetoDietMeals()
            MenuItemUi.MEAL_BY_DATE -> showMealByDate(mealsRepository)
            MenuItemUi.CALCULATED_CALORIES_PROTEIN_MEAL -> showMealsByCaloriesAndProtein()
            MenuItemUi.MEAL_BY_COUNTRY -> showMealByCountry()
            MenuItemUi.INGREDIENT_GAME_MEAL -> showIngredientGame()
            MenuItemUi.POTATO_MEALS -> showPotatoMeals()
            MenuItemUi.FOR_THIN_MEAL -> showForThinMeal(mealsRepository)
            MenuItemUi.SEAFOOD_MEALS -> showSeafoodMeals()
            MenuItemUi.ITALIAN_MEAL_FOR_GROUPS -> showItalianMealForGroups()
            MenuItemUi.EXIT -> Unit // Exit will break the loop
        }
    }

    private fun showHealthyFastFood(mealsRepository: MealsRepository) = handleAction {
        val allMeals = mealsRepository.getAllMeals()
        val healthyMeals = HealthyMealsFilter().getHealthyFastMeals(allMeals)

        println("=== Healthy Fast Meals take  15 minutes ===")
        if (healthyMeals.isEmpty()) {
            println("No healthy fast meals found.")
        } else {
            healthyMeals.forEachIndexed { index, meal ->
                println("${index + 1}. ${meal.name} - ${meal.minutes} min")
            }
        }
    }

    private fun showMealByName() = handleAction {
        val file = File("food.csv")
        val fileReader = CsvFileReader(file)
        val csvParser = CsvParserImpl()
        val mealsRepository = CsvMealsRepository(fileReader, csvParser)
        val searchAlgorithm = SearchAlgorithmFactory().createSearchAlgorithm()
        val searchByNameUseCase = SearchByNameUseCase(mealsRepository, searchAlgorithm)
        println("Enter the name of the meal")
        val query = readln()
        searchByNameUseCase.searchByName(query).onSuccess { meals ->
            meals.forEach { meal ->
                println(meal)
            }
        }.onFailure {
            println(it)
        }

    }

    private fun showIraqiMeals() = handleAction {
        val getIraqiMealsUseCase = GetIraqiMealsUseCase(mealsRepository)
        getIraqiMealsUseCase.getIraqiMeals().forEach {
            println(it)
        }
    }

    private fun showEasyFoodSuggestionGame() = handleAction {

        val getTenRandomEasyMealsUseCase = GetRandomMealsUseCase(mealsRepository)
        println("\n${MenuItemUi.EASY_FOOD_SUGGESTION_GAME}, TEN RANDOM MEALS : ")
        getTenRandomEasyMealsUseCase.getTenRandomEasyMeals().forEach { println("\nMeal Name is : ${it.name}\n") }

        print("\nEnter number of meals you want : ")
        val count = readln().trim().toIntOrNull()


        if (count != null)
            getTenRandomEasyMealsUseCase.getNRandomEasyMeals(count).forEach { println("\nMeal Name is : ${it.name}\n") }

    }

    private fun showPreparationTimeGuessingGame() = handleAction {
        val useCase = GuessGameUseCase(mealsRepository)

        useCase.getRandomGuessableMeal()
            .takeIf { it != null }
            ?.let { meal ->
                val correctTime = meal.minutes
                println("Guess Game: ${meal.name}")
                println("You have 3 attempts to guess the preparation time (in minutes):")

                repeat(3) { attempt ->
                    print("Attempt ${attempt + 1}: ")
                    val guess = readLine()?.toIntOrNull()

                    if (guess == null) {
                        println("Please enter a valid number.")
                        return@repeat
                    }

                    when (useCase.evaluateGuess(guess, correctTime)) {
                        GuessGameUseCase.GuessResult.CORRECT -> {
                            println("Correct answer! Preparation time is $correctTime  minutes")
                            return
                        }

                        GuessGameUseCase.GuessResult.TOO_LOW -> println("Less than the correct time.")
                        GuessGameUseCase.GuessResult.TOO_HIGH -> println("More than the correct time.")
                    }
                }

                println("Attempts are over. Correct time is: $correctTime minutes.")
            } ?: println("No meals suitable for the game.")
    }

    private fun showEggFreeSweets() = handleAction {
        println("I will Show you random Sweet with no eggs ")
        val getSweetsWithNoEggsUseCase = GetSweetsWithNoEggsUseCase(mealsRepository)
        try {
            var result: Meal
            do {
                result = getSweetsWithNoEggsUseCase.getRandomSweetWithNoEggs()
                println("meal name : ${result.name}")
                println("description : ${result.description}")
                print("Do you like that ? (y , n ) : ")
                val likeMeal = readln().trim()
            } while (likeMeal == "n")
            println(result)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun showKetoDietMeals() = handleAction {

        println("Welcome to your keto Diet Helper ")

        val ketoMealSuggestion = GetKetoDietMealsUseCase(mealsRepository)
        val message = "we suggest to you : \n"

        while (true) {
            try {

                println( message + ketoMealSuggestion.getNextKetoMeal())
            } catch (e: Exception) {
                println(e.message)
            }
            println("Do you want to see another keto meal suggestion? (y/n): ")
            when (readln().lowercase().trim()) {
                "y" -> println(" here is another one ")
                "n" -> break
            }
        }
    }

    private fun showMealByDate(mealsRepository: MealsRepository) = handleAction {
        print("Enter the date (dd-mm-yyyy): ")
        val date = readln()
        val resultMeals = GetByDateUseCase(mealsRepository).getByDate(date)
        resultMeals.onSuccess { meals ->
            println("Meals for date $date: \n")
            println(" ---------------------------------- ")
            println("|      ID       |         Name        ")
            println(" ---------------------------------- ")
            meals.forEach {
                println("|      ${it.id}      |     ${it.name}   ")
            }

            println("Enter the meal ID to get more details: ")
            val mealId = readln()
            if (mealId != "q") {  // Exit if user enters 'q'
                val mealResult = GetByIdUseCase().getById(mealId, meals)
                mealResult.onSuccess { println("Meal details:\n$it") }
                    .onFailure { error -> println("Sorry. ${error.message}") }
            }

        }.onFailure { error ->
            println("Sorry. ${error.message}")
        }
    }

    private fun showMealsByCaloriesAndProtein() = handleAction {
        println("--- Find Meals by Calories & Protein ---")
        print("Enter desired calories (e.g., 500): ")
        val caloriesInput = readln().toDoubleOrNull()
        print("Enter desired protein in grams (e.g., 30): ")
        val proteinInput = readln().toDoubleOrNull()

        if (caloriesInput != null && proteinInput != null) {
            val getMealsByCaloriesAndProteinUseCase = GetMealsContainsCaloriesProteinUseCase(mealsRepository)
            val meals =
                getMealsByCaloriesAndProteinUseCase.getMealsContainCaloriesAndProtein(caloriesInput, proteinInput)
            println("Meals with more than $caloriesInput calories and $proteinInput protein:")
            meals.forEach { meal ->
                val calories = meal.nutrition.calories.toString()
                val protein = meal.nutrition.protein.toString()
                println("- ${meal.name} (Calories: $calories, Protein: ${protein}g)")
            }
        } else {
            println("Invalid input. Please enter valid numbers.")
        }
    }

    private fun showMealByCountry() = handleAction {
        print("Enter Country and discover their meals : ")
        val countryName: String = readlnOrNull().toString()
        val exploreMealsByCountryUseCase = ExploreMealsByCountryUseCase(mealsRepository)

        exploreMealsByCountryUseCase.getLimitRandomMealsRelatedToCountry(countryName).forEach {
            println(it)
        }
    }

    private fun showIngredientGame() = handleAction {
        // Implement the logic for Ingredient Game
    }

    private fun showPotatoMeals() = handleAction {
        val potatoMealsUseCase = GetLimitRandomMealsIncludePotatoesUseCase(mealsRepository)
        println("=== Potato Meals ===")
        val potatoMeals = potatoMealsUseCase.getLimitRandomMealsIncludePotatoes()
        if (potatoMeals.isEmpty()) {
            println("No potato meals found.")
        } else {
            potatoMeals.forEachIndexed { index, meal ->
                println("${index + 1}. ${meal.name} - ${meal.description}")
            }
        }
        println("-------------------------------------------------------")
    }

    private fun showForThinMeal(mealsRepository: MealsRepository) = handleAction {
        println("=== Meal with high calories for Thin People ===")
        val suggestForThinMealsUseCase = GetCaloriesMoreThanUseCase(mealsRepository)

        while (true) {
            println("Here is a meal for you:")
            val meal = suggestForThinMealsUseCase.getNextMeal()

            println("\nMeal        : ${meal.name} ")
            println("Description : ${meal.description}\n")
            println("Did you like this meal? (y/n)")
            when (readln().trim().lowercase()) {
                "y" -> {
                    println("Great! Enjoy your meal.")
                    println(meal)
                    break
                }

                "n" -> {
                    println("Let's try another one.")
                }

                else -> {
                    println("Invalid input. Please enter 'y' or 'n'.")
                }
            }
        }
    }

    private fun showSeafoodMeals() = handleAction {
        val getRankedSeafoodByProteinUseCase = GetRankedSeafoodByProteinUseCase(
            mealsRepository
        )
        println("--- Seafood Meals Sorted by Protein (Highest First) ---")
        val rankedSeafoodMeals = getRankedSeafoodByProteinUseCase.getSeafoodMealsSortedByProtein()
        rankedSeafoodMeals.forEach { meal ->
            println(meal)
        }
        println("-------------------------------------------------------")
    }

    private fun showItalianMealForGroups() = handleAction {
        val getItalianMealsForLargeGroupsUseCase = GetItalianMealsForLargeGroupsUseCase(mealsRepository)
        getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups().forEach {
            println(it)
        }
    }

    private inline fun handleAction(action: () -> Unit) {
        try {
            action()
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}