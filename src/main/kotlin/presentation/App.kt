package presentation

import domain.use_case.fetch.GetIraqiMealsUseCase
import domain.use_case.fetch.GetMealsContainsPotatoUseCase
import domain.use_case.fetch.GetQuickHealthyMealsUseCase
import domain.use_case.fetch.GetSeafoodMealsUseCase
import domain.use_case.game.GuessPreparationTimeGameUseCase
import domain.use_case.game.IngredientGameUseCase
import domain.use_case.search.*
import domain.use_case.suggest.*
import model.gym_helper.CaloriesAndProteinTolerance
import model.gym_helper.GymHelperInput
import presentation.input_output.InputReader
import presentation.input_output.UiExecutor

class App(
    private val uiExecutor: UiExecutor,
    private val inputReader: InputReader,
    private val menu: Menu
) {
    fun start() {
        while (true) {
            uiExecutor.displayMenu(menu.getActions())
            val selectedAction = menu.getAction(inputReader.readIntOrNull()) ?: break

            try {
                selectedAction.execute(uiExecutor, inputReader)
            } catch (e: IllegalArgumentException) {
                uiExecutor.displayError(e.message ?: "Invalid input provided")
            } catch (e: Exception) {
                uiExecutor.displayError(e.message ?: "An unexpected error occurred")
            }

            uiExecutor.displayPrompt("Do you want to perform another action? (y/n): ")
            if (inputReader.readString().lowercase() != "y") {
                uiExecutor.displayResult("Goodbye")
                break
            }
        }
    }
}
class AppOld (
    private val getQuickHealthyMealsUseCase: GetQuickHealthyMealsUseCase,
    private val getByNameUseCase: SearchMealsByNameUseCase,
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase,
    private val suggestEasyFoodUseCase: SuggestEasyFoodUseCase,
    private val getsIngredientGameUseCase: IngredientGameUseCase,
    private val guessPreparationTimeGameUseCase: GuessPreparationTimeGameUseCase,
    private val getSweetsWithNoEggsUseCase: SuggestEggFreeSweetUseCase,
    private val suggestKetoMealUseCase: SuggestKetoMealUseCase,
    private val searchMealsByDateUseCase: SearchMealsByDateUseCase,
    private val searchGymFriendlyMealsUseCase: SearchGymFriendlyMealsUseCase,
    private val getMealsByCountryUseCase: SearchFoodByCultureUseCase,
    private val getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase,
    private val suggestHighCalorieMealsUseCase: SuggestHighCalorieMealsUseCase,
    private val getRankedSeafoodByProteinUseCase: GetSeafoodMealsUseCase,
    private val suggestItalianFoodForGroupUseCase: SuggestItalianFoodForGroupUseCase
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
            MenuItemUi.MEALS_BASED_ON_CALORIES_AND_PROTEINS -> showMealsByCaloriesAndProtein()
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
        val healthyMeals = getQuickHealthyMealsUseCase.getQuickHealthyMeals()
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
        println("Enter the name of the meal:")
        val query = readln()
        getByNameUseCase.searchMealsByName(query).onSuccess { meals ->
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
        println("\n${MenuItemUi.EASY_FOOD_SUGGESTION_GAME}, TEN RANDOM MEALS BY DEFAULT : ")
        suggestEasyFoodUseCase.getEasyFoodSuggestion().forEach { println("\nMeal Name is : ${it.name}\n") }

        print("\nEnter number of meals you want : ")
        val count = readln().trim().toIntOrNull()

        if (count != null)
            suggestEasyFoodUseCase.getEasyFoodSuggestion(count).forEach { println("\nMeal Name : ${it.name}\n") }
        println("------------------------------------------------------------")
    }

    private fun showPreparationTimeGuessingGame() = handleAction {
        guessPreparationTimeGameUseCase.getRandomMealWithPreparationTime()
            .takeIf { it != null }
            ?.let { meal ->
                val correctTime = meal.minutes
                println("---------- Guess Game ----------")
                println("Name of the meal: ${meal.name}")
                println("You have 3 attempts to guess the preparation time (in minutes):")

                repeat(3) { attempt ->
                    print("Attempt ${attempt + 1}: ")
                    val guess = readlnOrNull()?.toIntOrNull()

                    if (guess == null) {
                        println("Please enter a valid number.")
                        return@repeat
                    }

                    when (guessPreparationTimeGameUseCase.checkUserGuess(guess, correctTime)) {
                        GuessPreparationTimeGameUseCase.GuessResult.CORRECT -> {
                            println("Correct answer! Preparation time is $correctTime  minutes")
                            return
                        }

                        GuessPreparationTimeGameUseCase.GuessResult.TOO_LOW -> println("Less than the correct time.")
                        GuessPreparationTimeGameUseCase.GuessResult.TOO_HIGH -> println("More than the correct time.")
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
            val randomSweet = getSweetsWithNoEggsUseCase.suggestRandomSweet()

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

                println(message + suggestKetoMealUseCase.getNextKetoMeal())
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
        print("Enter the date (yyyy-mm-dd): ")
        val date = readln()
        val resultMeals = searchMealsByDateUseCase.searchMealsByDate(date)
            resultMeals.forEach {
            println("Meals for date $date: \n")
            println(" ---------------------------------- ")
            println("|      ID       |         Name        ")
            println(" ---------------------------------- ")
            println("|      ${it.id}      |     ${it.name}   ")
        }
            println("Enter the meal ID to get more details: ")
            val mealId = readln()
            if (mealId != "q") {  // Exit if user enters 'q'
                val mealResult = SearchMealsByIdUseCase().getById(mealId, resultMeals)
                mealResult.let{ println("Meal details:\n$it") }
            }
        println("------------------------------------------------------------")
    }

    private fun showMealsByCaloriesAndProtein() = handleAction {
        println("--- Find Meals by Calories & Protein ---")
        print("Enter desired calories (e.g., 500): ")
        val calories = readln().toDoubleOrNull()
        print("Enter desired protein (e.g., 30): ")
        val protein = readln().toDoubleOrNull()
        print("Enter calories tolerance (default is 30): ")
        val caloriesTolerance = readln().toIntOrNull() ?: 30
        print("Enter protein tolerance (default is 10): ")
        val proteinTolerance = readln().toIntOrNull() ?: 10
        if (
            calories == null || protein == null || caloriesTolerance < 0 || proteinTolerance < 0
        ) {
            println("Invalid input. Please enter numeric values.")
            return
        }
        val meals = searchGymFriendlyMealsUseCase.getMealsByCaloriesAndProtein(
            input = GymHelperInput(
                calories = calories,
                protein = protein,
                caloriesAndProteinTolerance = CaloriesAndProteinTolerance(
                    caloriesTolerance, proteinTolerance
                )
            )
        )
        if (meals.isEmpty()) {
            println("No meals found matching the criteria.")
        } else {
            println("Meals matching the criteria:")
            meals.forEachIndexed { index, meal ->
                println("------------------------------------------------------------")
                print("Meal ${index + 1}:")
                println("\n- ${meal.name}")
                println("Description: ${meal.description}")
                println("Calories: ${meal.nutrition.calories}")
                println("Protein: ${meal.nutrition.protein}")
                println("Preparation Time: ${meal.minutes} minutes")
            }
        }
        println("------------------------------------------------------------")
    }

    private fun showMealByCountry() = handleAction {
        print("Enter Country and discover their meals : ")

        var countryName: String = readlnOrNull().toString().trim()
        val exploreMeals = getMealsByCountryUseCase.exploreMealsRelatedToCountry(countryName)

        try {
            if (exploreMeals.isEmpty()) {
                print("No meals found for '$countryName'.")
            } else {
                println("Please Enter Your Country:$countryName")
                exploreMeals.forEachIndexed { index, meal ->
                    println("${index + 1} ${meal.name}")
                }
            }
        } catch (
            e: Exception
        ) {
            println("Error: ${e.message}")
        }
        println("------------------------------------------------------------")
    }

    private fun showIngredientGame() = handleAction {


        while (getsIngredientGameUseCase
                .getNextQuestion() != null
        ) {
            val question = getsIngredientGameUseCase.getNextQuestion()
            println("Guess the ingredient from the ingredients")
            println("Question mealName=${question!!.mealName} \noptions(${question.options})")

            print("Enter Answer : ")
            val answer = readln()
            if (getsIngredientGameUseCase.submitAnswer(answer, question!!.correctAnswer)) {
                getsIngredientGameUseCase
                    .getNextQuestion()
                    ?.let { println("Correct! Next question: ${it.mealName}") }
                println("Current Score: ${getsIngredientGameUseCase.getScore()} points")
            } else {
                println(" :( try again")
                println("Current Score: ${getsIngredientGameUseCase.getScore()} points")
                break
            }



        }
    }

    private fun showPotatoMeals() = handleAction {
        println("=== Potato Meals ===")
        val potatoMeals = getMealsContainsPotatoUseCase.getMealsContainsPotato()
        potatoMeals.forEach {
            println(it)
            }
        println("-------------------------------------------------------")
    }

    private fun showForThinMeal() = handleAction {
        println("=== Meal with high calories for Thin People ===")

        while (true) {
            println("Here is a meal for you:")
            val meal = suggestHighCalorieMealsUseCase.getNextMeal()

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
        val rankedSeafoodMeals = getRankedSeafoodByProteinUseCase.getSeafoodMeals()
        rankedSeafoodMeals.forEachIndexed { index, seafoodMeal ->
            println("${index + 1}. ${seafoodMeal.name} - Protein: ${seafoodMeal.protein}g")
        }
        println("-------------------------------------------------------")
    }

    private fun showItalianMealForGroups() = handleAction {
        suggestItalianFoodForGroupUseCase.suggestItalianMealsForLargeGroup().forEachIndexed { index, meal ->
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