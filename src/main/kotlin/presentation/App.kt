package presentation

import data.meal.CsvMealsRepository
import data.meal.MealsRepository
import domain.search.KMPSearchAlgorithm
import domain.search.LinearSearchAlgorithm
import domain.search.SearchAlgorithmFactory
import domain.use_case.*
import logic.use_case.GetKetoDietMealsUseCase


class App (
    private val healthyMealsFilterUseCase: HealthyMealsFilterUseCase,
//    private val searchByNameUseCase: SearchByNameUseCase,
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase,
    private val getRandomMealsUseCase: GetRandomMealsUseCase,
    private val guessGameUseCase: GuessGameUseCase,
    private val getSweetsWithNoEggsUseCase: GetSweetsWithoutEggsUseCase,
    private val getKetoDietMealsUseCase: GetKetoDietMealsUseCase,
    private val getByDateUseCase: GetByDateUseCase,
    private val getMealsContainsCaloriesProteinUseCase: GetMealsContainsCaloriesProteinUseCase,
    private val getMealsByCountryUseCase: GetMealsByCountryUseCase,
    private val getLimitRandomMealsIncludePotatoesUseCase: GetLimitRandomMealsIncludePotatoesUseCase,
    private val getMealsContainsHighCaloriesUseCase: GetMealsContainsHighCaloriesUseCase,
    private val getRankedSeafoodByProteinUseCase: GetRankedSeafoodByProteinUseCase,
    private val getItalianMealsForLargeGroupsUseCase: GetItalianMealsForLargeGroupsUseCase
) {
    fun start() {
        while (true) {
            printMenu()
            val selectedAction = getSelectedAction()

            if (selectedAction == MenuItemUi.EXIT) break

            executeAction(selectedAction)
            print("Do you want to perform another action? (y/n): ")
            val continueChoice = readln().lowercase()
            if (continueChoice != "y") {
                println("Goodbye")
                break
            }
        }
    }

    private fun printMenu() {
        MenuItemUi.entries.forEachIndexed { index, action ->
            println("${index + 1}- ${action.description}")
        }
        print(coloredPrompt("Choose the action from (1)..(15) or anything else to fetch: "))
    }

    private fun coloredPrompt(text: String): String {
        val yellow = "\u001B[33m"
        val reset = "\u001B[0m"
        return "$yellow$text$reset"
    }

    private fun getSelectedAction(): MenuItemUi {
        return readln().toIntOrNull()?.toMenuItem() ?: MenuItemUi.EXIT
    }


    private fun executeAction(selectedAction: MenuItemUi) {
        when (selectedAction) {
            MenuItemUi.HEALTHY_FAST_FOOD -> showHealthyFastFood()
            MenuItemUi.MEAL_BY_NAME -> showMealByName()
            MenuItemUi.IRAQI_MEALS -> showIraqiMeals()
            MenuItemUi.EASY_FOOD_SUGGESTION_GAME -> showEasyFoodSuggestionGame()
            MenuItemUi.PREPARATION_TIME_GUESSING_GAME -> showPreparationTimeGuessingGame()
            MenuItemUi.EGG_FREE_SWEETS -> showEggFreeSweets()
            MenuItemUi.KETO_DIET_MEAL -> showKetoDietMeals()
            MenuItemUi.MEAL_BY_DATE -> showMealByDate()
            MenuItemUi.CALCULATED_CALORIES_PROTEIN_MEAL -> showMealsByCaloriesAndProtein()
            MenuItemUi.MEAL_BY_COUNTRY -> showMealByCountry()
            MenuItemUi.INGREDIENT_GAME_MEAL -> showIngredientGame()
            MenuItemUi.POTATO_MEALS -> showPotatoMeals()
            MenuItemUi.FOR_THIN_MEAL -> showForThinMeal()
            MenuItemUi.SEAFOOD_MEALS -> showSeafoodMeals()
            MenuItemUi.ITALIAN_MEAL_FOR_GROUPS -> showItalianMealForGroups()
            MenuItemUi.EXIT -> println("See you soon!!")
        }
    }

    private fun showHealthyFastFood() = handleAction {
        val healthyMeals = healthyMealsFilterUseCase.getHealthyFastMeals()
        println("=== Healthy Fast Meals take  15 minutes ===")
        if (healthyMeals.isEmpty()) {
            println("No healthy fast meals found.")
        } else {
            healthyMeals.forEachIndexed { index, meal ->
                println("${index + 1}. ${meal.name} - ${meal.minutes} min")
            }
        }
        println("------------------------------------------------------------")
    }

    private fun showMealByName() = handleAction {
        val linearSearchAlgorithm = LinearSearchAlgorithm()
        val kmpSearchAlgorithm =  KMPSearchAlgorithm()
        val searchAlgorithmFactory = SearchAlgorithmFactory(
            linearSearchAlgorithm = linearSearchAlgorithm,
            kmpSearchAlgorithm = kmpSearchAlgorithm
        )
        val getByNameUseCase = GetByNameUseCase(mealsRepository, searchAlgorithmFactory)
        println("Enter the name of the meal:")
        val query = readln()
        getByNameUseCase.getByName(query).onSuccess { meals ->
            meals.forEach { meal ->
                println("\n Meal found:")
                println(meal)
            }
        }.onFailure {
            println(it)
        }
        println("------------------------------------------------------------")
    }

    private fun showIraqiMeals() = handleAction {
        getIraqiMealsUseCase.getIraqiMeals().forEach {
            println(it)
        }
    }

    private fun showEasyFoodSuggestionGame() = handleAction {
        println("\n${MenuItemUi.EASY_FOOD_SUGGESTION_GAME}, TEN RANDOM MEALS : ")
        getRandomMealsUseCase.getTenRandomEasyMeals().forEach { println("\nMeal Name is : ${it.name}\n") }

        print("\nEnter number of meals you want : ")
        val count = readln().trim().toIntOrNull()

        if (count != null)
            getRandomMealsUseCase.getNRandomEasyMeals(count).forEach { println("\nMeal Name is : ${it.name}\n") }
        println("------------------------------------------------------------")
    }

    private fun showPreparationTimeGuessingGame() = handleAction {
        guessGameUseCase.getRandomGuessableMeal()
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

                    when (guessGameUseCase.evaluateGuess(guess, correctTime)) {
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
        println("------------------------------------------------------------")
    }

    private fun showEggFreeSweets() = handleAction {

        println("I will Show you random Sweet without eggs please Wait ..... ")

        val endLoopText = "exit"
        var isUserLikeSweet = "n"

        do {
            val randomSweet = getSweetsWithNoEggsUseCase.getRandomSweet()

            randomSweet.fold(
                onSuccess = { sweet ->
                    println("__________________________________________________________\n" +
                            "You get this random sweet : \n" +
                            "Meal name   :  ${sweet.name} \n" +
                            "description :  ${sweet.description} \n" )

                    do{
                        print(
                                    "Do you like this sweet ? ( y , n )  \n"+
                                    "Your Choose : "
                        )
                        isUserLikeSweet = readln().trim()
                    }while (isUserLikeSweet != "y" && isUserLikeSweet != "n")


                    if (isUserLikeSweet == "y") {
                        println("your Final sweet is : /n $sweet")
                    }
                },
                onFailure = { exception ->
                    println(exception.message)
                    isUserLikeSweet = endLoopText
                }
            )

        } while (isUserLikeSweet == "n")

    }

    private fun showKetoDietMeals() = handleAction {

        println("Welcome to your keto Diet Helper ")
        val message = "we suggest to you : \n"

        while (true) {
            try {
                println( message + getKetoDietMealsUseCase.getNextKetoMeal())
            } catch (e: Exception) {
                println(e.message)
            }
            println("Do you want to see another keto meal suggestion? (y/n): ")
            when (readln().lowercase().trim()) {
                "y" -> println(" here is another one ")
                "n" -> break
            }
        }
        println("------------------------------------------------------------")
    }

    private fun showMealByDate() = handleAction {
        print("Enter the date (dd-mm-yyyy): ")
        val date = readln()
        val resultMeals = getByDateUseCase.getByDate(date)
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
        println("------------------------------------------------------------")
    }

    private fun showMealsByCaloriesAndProtein() = handleAction {
        println("--- Find Meals by Calories & Protein ---")
        print("Enter desired calories (e.g., 500): ")
        val caloriesInput = readln().toDoubleOrNull()
        print("Enter desired protein in grams (e.g., 30): ")
        val proteinInput = readln().toDoubleOrNull()

        if (caloriesInput != null && proteinInput != null) {
            val meals =
                getMealsContainsCaloriesProteinUseCase.getMealsContainCaloriesAndProtein(caloriesInput, proteinInput)
            println("Meals with more than $caloriesInput calories and $proteinInput protein:")
            meals.forEach { meal ->
                val calories = meal.nutrition.calories.toString()
                val protein = meal.nutrition.protein.toString()
                println("- ${meal.name} (Calories: $calories, Protein: ${protein}g)")
            }
        } else {
            println("Invalid input. Please enter valid numbers.")
        }
        println("------------------------------------------------------------")
    }

    private fun showMealByCountry() = handleAction {
        print("Enter Country and discover their meals : ")

        var countryName: String = readlnOrNull().toString().trim()
        val exploreMeals = getMealsByCountryUseCase.getLimitRandomMealsRelatedToCountry(countryName)

        try {
            if (exploreMeals.isEmpty()) {
                println("No meals found for '$countryName'.")
            }else {
                println("Meals related to '$countryName':")
                exploreMeals.forEachIndexed { index, meal ->
                    println("${index + 1} ${meal.name}")
                }
            }
        }catch (
            e: Exception
        ){
            println("Error: ${e.message}")
        }
        println("------------------------------------------------------------")
    }

    private fun showIngredientGame() = handleAction {
        // Implement the logic for Ingredient Game
    }

    private fun showPotatoMeals() = handleAction {
        println("=== Potato Meals ===")
        val potatoMeals = getLimitRandomMealsIncludePotatoesUseCase.getLimitRandomMealsIncludePotatoes()
        if (potatoMeals.isEmpty()) {
            println("No potato meals found.")
        } else {
            potatoMeals.forEachIndexed { index, meal ->
                println("${index + 1}. ${meal.name} - ${meal.description}")
            }
        }
        println("-------------------------------------------------------")
    }

    private fun showForThinMeal() = handleAction {
        println("=== Meal with high calories for Thin People ===")

        while (true) {
            println("Here is a meal for you:")
            val meal = getMealsContainsHighCaloriesUseCase.getNextMeal()

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
            println("------------------------------------------------------------")
        }
    }

    private fun showSeafoodMeals() = handleAction {
        println("--- Seafood Meals Sorted by Protein (Highest First) ---")
        val rankedSeafoodMeals = getRankedSeafoodByProteinUseCase.getSeafoodMealsSortedByProtein()
        rankedSeafoodMeals.forEach { meal ->
            println(meal)
        }
        println("-------------------------------------------------------")
    }

    private fun showItalianMealForGroups() = handleAction {
        getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups().forEachIndexed { index, meal ->
            println("${index + 1}. ${meal.name}")
        }
        println("------------------------------------------------------------")
    }

    private inline fun handleAction(action: () -> Unit) {
        try {
            action()
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}