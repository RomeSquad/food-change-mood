package domain.use_case.search.utils

import data.model.Meal

class DateValidator {
    fun validate(date: String) {
        when {
            date.isBlank() -> throw IllegalArgumentException("Date cannot be empty")
            !date.matches(Meal.Companion.DATE_REGEX) -> throw IllegalArgumentException(
                "Invalid date format: $date. Expected format: ${Meal.Companion.DATE_FORMAT}"
            )
        }
    }
}