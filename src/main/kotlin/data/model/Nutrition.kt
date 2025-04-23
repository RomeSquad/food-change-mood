package data.model

data class Nutrition(
    val calories: Double,
    val totalFat: Double,
    val sugar: Double,
    val sodium: Double,
    val protein: Double,
    val saturatedFat: Double,
    val carbohydrates: Double
) {
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