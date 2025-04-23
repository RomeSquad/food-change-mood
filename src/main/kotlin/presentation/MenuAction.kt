package presentation

import domain.use_case.GetByNameUseCase
import domain.use_case.GetHealthyMealsFilterUseCase
import presentation.input_output.InputReader
import presentation.input_output.UiExecutor

interface MenuAction {
    val description: String
    fun execute(ui: UiExecutor, inputReader: InputReader)
}

class HealthyFastFoodAction(
    private val getQuickHealthyMealsUseCase: GetQuickHealthyMealsUseCase
) : MenuAction {
    override val description: String = "Healthy Fast Meals (15 minutes)"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        val healthyMeals = getQuickHealthyMealsUseCase.getQuickHealthyMeals()
        ui.displayResult("=== Healthy Fast Meals take 15 minutes ===")
        if (healthyMeals.isEmpty()) {
            ui.displayResult("No healthy fast meals found.")
        } else {
            healthyMeals.forEachIndexed { index, meal ->
                ui.displayResult("${index + 1}. ${meal.name} - ${meal.minutes} min")
            }
        }
        ui.displayResult("------------------------------------------------------------")
    }
}
class MealByNameAction(
    private val getByNameUseCase: SearchMealsByNameUseCase
) : MenuAction {
    override val description: String = "Search Meal by Name"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayPrompt("Enter the name of the meal: ")
        val query = inputReader.readString()
        getByNameUseCase.searchMealsByName(query).onSuccess { meals ->
            meals.forEach { meal ->
                ui.displayResult("\nMeal found:")
                ui.displayResult(meal.toString())
            }
        }.onFailure { error ->
            ui.displayError(error.message ?: "Failed to find meal")
        }
        ui.displayResult("------------------------------------------------------------")
    }
}
class EasyFoodSuggestionAction(
    private val getRandomMealsUseCase: GetRandomMealsUseCase
) : MenuAction {
    override val description: String = "Easy Food Suggestion Game"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("\nEasy Food Suggestion Game, TEN RANDOM MEALS BY DEFAULT:")
        getRandomMealsUseCase.getNRandomEasyMeals().forEach { meal ->
            ui.displayResult("\nMeal Name is: ${meal.name}\n")
        }

        ui.displayPrompt("\nEnter number of meals you want: ")
        val count = inputReader.readIntOrNull()

        if (count != null) {
            getRandomMealsUseCase.getNRandomEasyMeals(count).forEach { meal ->
                ui.displayResult("\nMeal Name: ${meal.name}\n")
            }
        }
        ui.displayResult("------------------------------------------------------------")
    }
}
class PreparationTimeGuessingAction(
    private val guessGameUseCase: GuessGameUseCase
) : MenuAction {
    override val description: String = "Preparation Time Guessing Game"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        guessGameUseCase.getRandomMealWithPreparationTime()
            ?.let { meal ->
                val correctTime = meal.minutes
                ui.displayResult("---------- Guess Game ----------")
                ui.displayResult("Name of the meal: ${meal.name}")
                ui.displayResult("You have 3 attempts to guess the preparation time (in minutes):")

                repeat(3) { attempt ->
                    ui.displayPrompt("Attempt ${attempt + 1}: ")
                    val guess = inputReader.readIntOrNull()

                    if (guess == null) {
                        ui.displayResult("Please enter a valid number.")
                        return@repeat
                    }

                    when (guessGameUseCase.checkUserGuess(guess, correctTime)) {
                        GuessGameUseCase.GuessResult.CORRECT -> {
                            ui.displayResult("Correct answer! Preparation time is $correctTime minutes")
                            return@let
                        }
                        GuessGameUseCase.GuessResult.TOO_LOW -> ui.displayResult("Less than the correct time.")
                        GuessGameUseCase.GuessResult.TOO_HIGH -> ui.displayResult("More than the correct time.")
                    }
                }

                ui.displayResult("Attempts are over. Correct time is: $correctTime minutes.")
            } ?: ui.displayResult("No meals suitable for the game.")
        ui.displayResult("------------------------------------------------------------")
    }
}
class EggFreeSweetsAction(
    private val getSweetsWithNoEggsUseCase: GetSweetsWithoutEggsUseCase
) : MenuAction {
    override val description: String = "Egg-Free Sweets"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("I will show you a random sweet without eggs, please wait...")

        val endLoopText = "exit"
        var isUserLikeSweet = "n"

        do {
            val randomSweet = getSweetsWithNoEggsUseCase.getRandomSweet()

            randomSweet.fold(
                onSuccess = { sweet ->
                    ui.displayResult(
                        "__________________________________________________________\n" +
                                "You get this random sweet:\n" +
                                "Meal name: ${sweet.name}\n" +
                                "Description: ${sweet.description}\n"
                    )

                    do {
                        ui.displayPrompt("Do you like this sweet? (y/n)\nYour choice: ")
                        isUserLikeSweet = inputReader.readString().trim()
                    } while (isUserLikeSweet != "y" && isUserLikeSweet != "n")

                    if (isUserLikeSweet == "y") {
                        ui.displayResult("Your final sweet is:\n$sweet")
                    }
                },
                onFailure = { exception ->
                    ui.displayError(exception.message ?: "Failed to fetch a sweet")
                    isUserLikeSweet = endLoopText
                }
            )
        } while (isUserLikeSweet == "n")
    }
}
class MealByDateAction(
    private val getByDateUseCase: GetByDateUseCase
) : MenuAction {
    override val description: String = "Meals by Date"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayPrompt("Enter the date (yyyy-mm-dd): ")
        val date = inputReader.readString()
        val resultMeals = getByDateUseCase.getByDate(date)

        resultMeals.forEach { meal ->
            ui.displayResult("Meals for date $date:\n")
            ui.displayResult(" ---------------------------------- ")
            ui.displayResult("|      ID       |         Name        ")
            ui.displayResult(" ---------------------------------- ")
            ui.displayResult("|      ${meal.id}      |     ${meal.name}   ")
        }

        ui.displayPrompt("Enter the meal ID to get more details (or 'q' to skip): ")
        val mealId = inputReader.readString()
        if (mealId != "q") {
            val mealResult = GetByIdUseCase().getById(mealId, resultMeals)
            mealResult?.let { ui.displayResult("Meal details:\n$it") }
        }
        ui.displayResult("------------------------------------------------------------")
    }
}
class MealsByCaloriesAndProteinAction(
    private val gymHelperUseCase: GymHelperUseCase
) : MenuAction {
    override val description: String = "Meals Based on Calories and Proteins"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("--- Find Meals by Calories & Protein ---")
        ui.displayPrompt("Enter desired calories (e.g., 500): ")
        val calories = inputReader.readDoubleOrNull()
        ui.displayPrompt("Enter desired protein (e.g., 30): ")
        val protein = inputReader.readDoubleOrNull()
        ui.displayPrompt("Enter calories tolerance (default is 30): ")
        val caloriesTolerance = inputReader.readIntOrNull() ?: 30
        ui.displayPrompt("Enter protein tolerance (default is 10): ")
        val proteinTolerance = inputReader.readIntOrNull() ?: 10

        if (calories == null || protein == null || caloriesTolerance < 0 || proteinTolerance < 0) {
            ui.displayResult("Invalid input. Please enter numeric values.")
            return
        }

        val meals = gymHelperUseCase.getMealsByCaloriesAndProtein(
            input = GymHelperInput(
                calories = calories,
                protein = protein,
                caloriesAndProteinTolerance = CaloriesAndProteinTolerance(
                    caloriesTolerance, proteinTolerance
                )
            )
        )

        if (meals.isEmpty()) {
            ui.displayResult("No meals found matching the criteria.")
        } else {
            ui.displayResult("Meals matching the criteria:")
            meals.forEachIndexed { index, meal ->
                ui.displayResult("------------------------------------------------------------")
                ui.displayResult("Meal ${index + 1}:")
                ui.displayResult("\n- ${meal.name}")
                ui.displayResult("Description: ${meal.description}")
                ui.displayResult("Calories: ${meal.nutrition.calories}")
                ui.displayResult("Protein: ${meal.nutrition.protein}")
                ui.displayResult("Preparation Time: ${meal.minutes} minutes")
            }
        }
        ui.displayResult("------------------------------------------------------------")
    }
}
class MealByCountryAction(
    private val getMealsByCountryUseCase: GetMealsByCountryUseCase
) : MenuAction {
    override val description: String = "Meals by Country"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayPrompt("Enter a country to discover their meals: ")
        val countryName = inputReader.readString().trim()
        val exploreMeals = getMealsByCountryUseCase.getLimitRandomMealsRelatedToCountry(countryName)

        try {
            if (exploreMeals.isEmpty()) {
                ui.displayResult("No meals found for '$countryName'.")
            } else {
                ui.displayResult("Meals from $countryName:")
                exploreMeals.forEachIndexed { index, meal ->
                    ui.displayResult("${index + 1} ${meal.name}")
                }
            }
        } catch (e: Exception) {
            ui.displayError(e.message ?: "Error fetching meals for country")
        }
        ui.displayResult("------------------------------------------------------------")
    }
}
class IngredientGameAction(
    private val getIngredientGameUseCase: GetIngredientGameUseCase
) : MenuAction {
    override val description: String = "Ingredient Guessing Game"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        while (getIngredientGameUseCase.correctCount != 15) {
            val question = getIngredientGameUseCase.getNextQuestion()
            if (question == null) {
                ui.displayResult("No more questions available for the game.")
                break
            }

            ui.displayResult("Guess the ingredient from the ingredients")
            ui.displayResult("Question mealName=${question.mealName}\noptions(${question.options})")
            ui.displayPrompt("Enter Answer: ")
            val answer = inputReader.readString()

            if (getIngredientGameUseCase.submitAnswer(answer, question.correctAnswer)) {
                getIngredientGameUseCase.correctCount++
                ui.displayResult("Current Score: ${getIngredientGameUseCase.getScore()} points")
            } else {
                ui.displayResult(":( Try again")
                ui.displayResult("Current Score: ${getIngredientGameUseCase.getScore()} points")
                break
            }
        }
    }
}
class PotatoMealsAction(
    private val getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase
) : MenuAction {
    override val description: String = "Potato Meals"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("=== Potato Meals ===")
        val potatoMeals = getMealsContainsPotatoUseCase.getMealsContainsPotato()
        potatoMeals.forEach { meal ->
            ui.displayResult(meal.toString())
        }
        ui.displayResult("-------------------------------------------------------")
    }
}
class HighCaloriesAction(
    private val getMealsContainsHighCaloriesUseCase: GetMealsContainsHighCaloriesUseCase
) : MenuAction {
    override val description: String = "High-Calorie Meals for Thin People"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("=== Meal with High Calories for Thin People ===")

        while (true) {
            ui.displayResult("Here is a meal for you:")
            val meal = getMealsContainsHighCaloriesUseCase.getNextMeal()

            ui.displayResult("\nMeal: ${meal.name}")
            ui.displayResult("Description: ${meal.description}\n")
            ui.displayPrompt("Did you like this meal? (y/n): ")
            when (inputReader.readString().trim().lowercase()) {
                "y" -> {
                    ui.displayResult("Great! Enjoy your meal.")
                    ui.displayResult(meal.toString())
                    break
                }
                "n" -> ui.displayResult("Let's try another one.")
                else -> ui.displayError("Invalid input. Please enter 'y' or 'n'.")
            }
            ui.displayResult("------------------------------------------------------------")
        }
    }
}
class SeafoodMealsAction(
    private val getRankedSeafoodByProteinUseCase: GetSeafoodMealsUseCase
) : MenuAction {
    override val description: String = "Seafood Meals Sorted by Protein"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("--- Seafood Meals Sorted by Protein (Highest First) ---")
        val rankedSeafoodMeals = getRankedSeafoodByProteinUseCase.getSeafoodMeals()
        rankedSeafoodMeals.forEachIndexed { index, seafoodMeal ->
            ui.displayResult("${index + 1}. ${seafoodMeal.name} - Protein: ${seafoodMeal.protein}g")
        }
        ui.displayResult("-------------------------------------------------------")
    }
}
class ItalianMealForGroupsAction(
    private val getItalianMealsForLargeGroupsUseCase: GetItalianMealsForLargeGroupsUseCase
) : MenuAction {
    override val description: String = "Italian Meals for Large Groups"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups().forEachIndexed { index, meal ->
            ui.displayResult("${index + 1}. ${meal.name}")
        }
        ui.displayResult("------------------------------------------------------------")
    }
}
class KetoDietAction(
    private val getKetoDietMealsUseCase: GetKetoDietMealsUseCase
) : MenuAction {
    override val description: String = "Keto Diet Meal Suggestions"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        ui.displayResult("Welcome to your Keto Diet Helper")
        val message = "We suggest to you:\n"

        while (true) {
            try {
                ui.displayResult(message + getKetoDietMealsUseCase.getNextKetoMeal().toString())
            } catch (e: Exception) {
                ui.displayError(e.message ?: "Error fetching keto meal")
            }
            ui.displayPrompt("Do you want to see another keto meal suggestion? (y/n): ")
            when (inputReader.readString().lowercase().trim()) {
                "y" -> ui.displayResult("Here is another one")
                "n" -> break
                else -> ui.displayError("Please enter 'y' or 'n'")
            }
        }
        ui.displayResult("------------------------------------------------------------")
    }
}
class IraqiMealsAction(
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase
) : MenuAction {
    override val description: String = "Iraqi Meals"

    override fun execute(ui: UiExecutor, inputReader: InputReader) {
        getIraqiMealsUseCase.getIraqiMeals().forEach { meal ->
            ui.displayResult(meal.toString())
        }
        ui.displayResult("------------------------------------------------------------")
    }
}