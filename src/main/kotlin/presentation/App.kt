package presentation

import data.CsvMealsRepository
import logic.MealsRepository
import logic.use_case.*

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
            MenuItemUi.HEALTHY_FAST_FOOD -> showHealthyFastFood()
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
            MenuItemUi.FOR_THIN_MEAL -> showForThinMeal()
            MenuItemUi.SEAFOOD_MEALS -> showSeafoodMeals()
            MenuItemUi.ITALIAN_MEAL_FOR_GROUPS -> showItalianMealForGroups()
            MenuItemUi.EXIT -> Unit // Exit will break the loop
        }
    }

    private fun showHealthyFastFood() = handleAction {
        // Implement the logic for Healthy Fast Food
    }


    private fun showMealByName() = handleAction {
        // Implement the logic for Meal By Name
    }

    private fun showIraqiMeals() = handleAction {
        // Implement the logic for Iraqi Meals
    }

    private fun showEasyFoodSuggestionGame() = handleAction {
        // Implement the logic for Easy Food Suggestion Game
    }

    private fun showPreparationTimeGuessingGame() = handleAction {
        // Implement the logic for Preparation Time Guessing Game
    }

    private fun showEggFreeSweets() = handleAction {
        // Implement the logic for Egg-Free Sweets
    }

    private fun showKetoDietMeals() = handleAction {
        // Implement the logic for Keto Diet Meals
    }

    private fun showMealByDate(mealsRepository: MealsRepository) = handleAction {
        print("Enter the date (dd-mm-yyyy): ")
        val date = readln()
        val resultMeals = GetByDate(mealsRepository).getByDate(date)
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
                val mealResult = GetById().getById(mealId, meals)
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

    private fun showForThinMeal() = handleAction {
        // Implement the logic for For Thin Meal
    }

    private fun showSeafoodMeals() = handleAction {
        // Implement the logic for Seafood Meals
    }

    private fun showItalianMealForGroups() = handleAction {
        // Implement the logic for Italian Meal for Groups
    }

    private inline fun handleAction(action: () -> Unit) {
        try {
            action()
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}