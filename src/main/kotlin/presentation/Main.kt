import data.CsvMealsRepository
import data.utils.CsvFileReader
import data.utils.CsvParserImpl
import logic.use_case.GetTenRandomEasyMealsUseCase
import java.io.File

fun main (){
    val file = File("food.csv")
    val fileReader = CsvFileReader(file)

    val csvParser = CsvParserImpl()
    val mealsRepository = CsvMealsRepository(fileReader,csvParser)
    //mealsRepository.getAllMeals().let { println(it.size) }
    // test your code here

//    val meals = mealsRepository.getAllMeals().take(100)
//    println("Meals loaded: ${meals.size}")

    val use = GetTenRandomEasyMealsUseCase(mealsRepository)
    val meal = use.getTenRandomEasyMeals()

    println(meal)

    //println("Meals loaded: ${mealsRepository.getAllMeals().size}")


//    val useCase = GuessGameUseCase(mealsRepository)
//    val meal = useCase.getRandomGuessableMeal()
//
//    if (meal == null) {
//        println("❌ There are no game-appropriate meals.")
//        return
//    }
//
//    val correctTime = meal.minutes
//    println("🍽 Guess Game: ${meal.name}")
//    println("You have 3 attempts to guess the preparation time (in minutes):")
//
//    repeat(3) { attempt ->
//        print("🔹 Try ${attempt + 1}: ")
//        val guess = readLine()?.toIntOrNull()
//
//        if (guess == null) {
//            println("Please enter a valid number.")
//            return@repeat
//        }
//
//        when (useCase.evaluateGuess(guess, correctTime)) {
//            GuessGameUseCase.GuessResult.CORRECT -> {
//                println("✅ Correct answer! Preparation time is $correctTime minute")
//                return
//            }
//            GuessGameUseCase.GuessResult.TOO_LOW -> println("❌ Less than the correct time.")
//            GuessGameUseCase.GuessResult.TOO_HIGH -> println("❌ More than the right time.")
//        }
//    }
//
//    println("📢 Attempts are over. The correct time is: $correctTime minute.")


}