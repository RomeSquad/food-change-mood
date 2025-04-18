package presentation

import data.CsvMealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParserImpl
import logic.MealsRepository
import logic.use_case.*
import logic.utils.SearchAlgorithmFactory
import model.Meal
import java.io.File
import logic.use_case.HealthyMealsFilter

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
        // Implement the logic for Iraqi Meals
        val identifyIraqiMealsUseCase = IdentifyIraqiMealsUseCase(mealsRepository)
        identifyIraqiMealsUseCase.identifyIraqiMeals().forEach {
            println(it)
        }
    }

    private fun showEasyFoodSuggestionGame() = handleAction {
        val getTenRandomEasyMealsUseCase = GetTenRandomEasyMealsUseCase(mealsRepository)
        println(MenuItemUi.EASY_FOOD_SUGGESTION_GAME)
        println(getTenRandomEasyMealsUseCase.getTenRandomEasyMeals())
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

        val getSweetsWithNoEggsUseCase = GetSweetsWithNoEggsUseCase(mealsRepository)
        try{
            var result : Meal
            do {
                result = getSweetsWithNoEggsUseCase.getRandomSweetWithNoEggs()
                println("meal name : ${result.name}")
                println("description : ${result.description}")
                print("Do you like that ? (y , n ) : ")
                val likeMeal = readln().trim()
            }while (likeMeal == "n")
            println(result)
        }catch (e:Exception){
            println( e.message )
        }
    }

    private fun showKetoDietMeals() = handleAction {
        // Implement the logic for Keto Diet Meals
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
        // Implement the logic for Meals by Calories and Protein
    }

    private fun showMealByCountry() = handleAction {
        // Implement the logic for Meal By Country
    }

    private fun showIngredientGame() = handleAction {
        // Implement the logic for Ingredient Game
    }

    private fun showPotatoMeals() = handleAction {
        // Implement the logic for Potato Meals
    }

    private fun showForThinMeal(mealsRepository: MealsRepository) = handleAction {
        val calories = 700.0
        val suggestForThinMealsUseCase = GetCaloriesMoreThanUseCase(mealsRepository).getCaloriesMoreThan(calories)
        suggestForThinMealsUseCase.onSuccess { mealsVal ->
            var meals = mealsVal
            while (meals.isNotEmpty()) {
                val randomIndex = (Math.random() * meals.size).toInt()
                val randomMeal = meals[randomIndex]
                println("Here is a meal for you:")
                println(randomMeal.name)
                println(randomMeal.description)
                println("Did you like the meal? (y/n)")
                val answer = readln()
                if (answer.lowercase() == "y") {
                    println("Here is the meal you selected:\n$randomMeal")
                    return
                } else {
                    println("Lets try another meal")
                    meals = meals.filter { it != randomMeal }
                }
            }
            println("Sorry, we don't have any more meals for you.")
        }.onFailure { error ->
            println("Sorry. ${error.message}")
        }
    }

    private fun showSeafoodMeals() = handleAction {
        // Implement the logic for Seafood Meals
    }

    private fun showItalianMealForGroups() = handleAction {
        // Implement the logic for Italian Meal for Groups
        val suggestItalianMealsForLargeGroupsUseCase = SuggestItalianMealsForLargeGroupsUseCase(mealsRepository)
        suggestItalianMealsForLargeGroupsUseCase.suggestItalianMealsForLargeGroups().forEach {
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