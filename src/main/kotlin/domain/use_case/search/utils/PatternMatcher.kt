package domain.use_case.search.utils

interface PatternMatcher {
    fun match(text: String, pattern: String): Boolean
}