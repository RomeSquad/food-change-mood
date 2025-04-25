package data.model

data class Nutrition(
    val protein: Double?,
    val calories: Double = 0.0,
    val totalFat: Double = 0.0,
    val sugar: Double = 0.0,
    val sodium: Double = 0.0,
    val saturatedFat: Double = 0.0,
    val carbohydrates: Double = 0.0
)
 {
    override fun toString(): String {
        return String.format(
            "%-10s %-18s %-13s %-13s %-15s %-15s %-15s %-13s",
            "Nutrition",
            "calories = $calories",
            "fat = $totalFat",
            "sugar = $sugar",
            "sodium = $sodium",
            "protein = $protein",
            "satFat = $saturatedFat",
            "carbs = $carbohydrates"
        )
    }
}