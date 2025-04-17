package model

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
        return "Nutrition: calories=$calories, totalFat=$totalFat, sugar=$sugar, sodium=$sodium, protein=$protein, saturatedFat=$saturatedFat, carbohydrates=$carbohydrates"
    }
}