package presentation

enum class MenuItemUi(val description: String) {
    HEALTHY_FAST_FOOD("Get Healthy Fast-Food Meal"),
    MEAL_BY_NAME("Find Meal By Name"),
    IRAQI_MEALS("Show Iraqi Meals"),
    EASY_FOOD_SUGGESTION_GAME("Suggest 10 Random Meals"),
    PREPARATION_TIME_GUESSING_GAME("Guess Preparation Time"),
    EGG_FREE_SWEETS("Find Egg-Free Sweets"),
    KETO_DIET_MEAL("Discover Keto Meals"),
    MEAL_BY_DATE("Find Meals by Date"),
    CALCULATED_CALORIES_PROTEIN_MEAL("Get Calorie And Protein-Tracked Meals"),
    MEAL_BY_COUNTRY("Explore Meals by Country"),
    INGREDIENT_GAME_MEAL("Guess the Ingredient Game"),
    POTATO_MEALS("Get names of 10 meals that contains potatoes in ingredients for this meal"),
    FOR_THIN_MEAL("High-Calories Meals for Weight Gain"),
    SEAFOOD_MEALS("Explore Seafood Dishes"),
    ITALIAN_MEAL_FOR_GROUPS("Italian Group Meals"),
    EXIT("Exit");
}

fun Int.toMenuItem() = MenuItemUi.entries.getOrNull(this - 1) ?: MenuItemUi.EXIT