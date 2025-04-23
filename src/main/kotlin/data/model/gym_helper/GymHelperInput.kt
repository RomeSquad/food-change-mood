package data.model.gym_helper

data class GymHelperInput(
    val calories: Double,
    val protein: Double,
    val caloriesAndProteinTolerance: CaloriesAndProteinTolerance
)

data class CaloriesAndProteinTolerance(
    val caloriesTolerance: Int,
    val proteinTolerance: Int
)
