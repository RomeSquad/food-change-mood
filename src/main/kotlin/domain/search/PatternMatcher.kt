package domain.search

interface PatternMatcher {
    fun match(text: String, pattern: String): Boolean
}