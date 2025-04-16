package presentation

import data.CsvMealsRepository
import logic.GetMealsContainsCaloriesProteinUseCase
import logic.IdentifyIraqiMealsUseCase
import logic.MealsRepository
import logic.use_case.GetLimitRandomMealsIncludePotatoesUseCase
import logic.use_case.GetTenRandomEasyMealsUseCase

class App(
    private val mealsRepository: CsvMealsRepository,
    private val getLimitRandomMealsIncludePotatoesUseCase: GetLimitRandomMealsIncludePotatoesUseCase,
    private val getTenRandomEasyMealsUseCase: GetTenRandomEasyMealsUseCase,
    private val identifyIraqiMealsUseCase: IdentifyIraqiMealsUseCase,
    private val getMealsContainsCaloriesProteinUseCase: GetMealsContainsCaloriesProteinUseCase
) {
    fun start() {
        do {
            printMenu()
            val selectedAction = getSelectedAction()
            if (selectedAction == MenuItemUi.EXIT) break

            executeAction(selectedAction, mealsRepository)
        } while (true)
    }

    private fun printMenu() {
        MenuItemUi.entries.forEachIndexed { index, action ->
            println("${index + 1}- ${action.description}")
        }
        print("Choose the action \u001B[33m*enter (8) or anything else to exit*\u001B[0m: ")
    }

    private fun getSelectedAction(): MenuItemUi {
        return (readln().toIntOrNull() ?: -1).toMenuItem()
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
            MenuItemUi.MEAL_BY_DATE -> showMealByDate()
            MenuItemUi.CALCULATED_CALORIES_MEAL -> showMealsByCaloriesAndProtein()
            MenuItemUi.MEAL_BY_COUNTRY -> showMealByCountry()
            MenuItemUi.INGREDIENT_GAME -> showIngredientGame()
            MenuItemUi.POTATO_MEALS -> showPotatoMeals(mealsRepository)
            MenuItemUi.FOR_THIN_MEAL -> showForThinMeal()
            MenuItemUi.SEAFOOD_MEALS -> showSeafoodMeals()
            MenuItemUi.ITALIAN_MEAL_FOR_GROUPS -> showItalianMealForGroups()
            MenuItemUi.EXIT -> Unit // Exit will break the loop
        }
    }

    private fun showHealthyFastFood() {
        getTenRandomEasyMealsUseCase.getTenRandomEasyMeals().forEach { println(it) }
    }

    private fun showMealByName() {
        // Implement the logic for Meal By Name
    }

    private fun showIraqiMeals() {
        identifyIraqiMealsUseCase.identifyIraqiMeals().forEach { println(it.name) }
    }

    private fun showEasyFoodSuggestionGame() {
        // Implement the logic for Easy Food Suggestion Game
    }

    private fun showPreparationTimeGuessingGame() {
        // Implement the logic for Preparation Time Guessing Game
    }

    private fun showEggFreeSweets() {
        // Implement the logic for Egg-Free Sweets
    }

    private fun showKetoDietMeals() {
        // Implement the logic for Keto Diet Meals
    }

    private fun showMealByDate() {
        // Implement the logic for Meal by Date
    }

    private fun showMealsByCaloriesAndProtein() {
        getMealsContainsCaloriesProteinUseCase
            .getMealsContainCaloriesAndProtein(targetCalories = 200.0, targetProtein = 20.0)
            .forEach { println("${it.name}: ${it.nutrition.calories} calories, ${it.nutrition.protein}g protein") }
    }

    private fun showMealByCountry() {
        // Implement the logic for Meal By Country
    }

    private fun showIngredientGame() {
        // Implement the logic for Ingredient Game
    }

    private fun showPotatoMeals(mealsRepository: MealsRepository) {
        getLimitRandomMealsIncludePotatoesUseCase
            .getLimitRandomMealsIncludePotatoes(10)
            .forEach { println(it) }
    }

    private fun showForThinMeal() {
        // Implement the logic for For Thin Meal
    }

    private fun showSeafoodMeals() {
        // Implement the logic for Seafood Meals
    }

    private fun showItalianMealForGroups() {
        // Implement the logic for Italian Meal for Groups
    }
}