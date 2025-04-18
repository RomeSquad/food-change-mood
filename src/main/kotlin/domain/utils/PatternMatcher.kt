package domain.utils

interface PatternMatcher {
    fun match(text: String, pattern: String): Boolean
}