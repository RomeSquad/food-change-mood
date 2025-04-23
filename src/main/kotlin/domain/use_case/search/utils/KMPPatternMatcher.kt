package domain.use_case.search.utils

class KMPPatternMatcher : PatternMatcher {
    override fun match(text: String, pattern: String): Boolean {
        if (pattern.isBlank()) return true
        if (text.isBlank()) return false

        val lps = computeLPSArray(pattern)

        return generateSequence(Pair(0, 0)) { (textIndex, patternIndex) ->
            when {
                textIndex >= text.length -> null
                pattern[patternIndex].equals(text[textIndex], ignoreCase = true) ->
                    Pair(textIndex + 1, patternIndex + 1)
                patternIndex != 0 ->
                    Pair(textIndex, lps[patternIndex - 1])
                else ->
                    Pair(textIndex + 1, 0)
            }
        }.any { (_, patternIndex) -> patternIndex == pattern.length }
    }
    private fun computeLPSArray(pattern: String): IntArray {
        if (pattern.isEmpty()) return intArrayOf()

        return buildList {
            add(0)
            pattern.drop(1).foldIndexed(0) { i, prefixLength, currentChar ->
                var updatedPrefix = prefixLength
                while (
                    updatedPrefix > 0 &&
                    !currentChar.equals(pattern[updatedPrefix], ignoreCase = true)
                ) {
                    updatedPrefix = this[updatedPrefix - 1]
                }

                if (currentChar.equals(pattern[updatedPrefix], ignoreCase = true)) {
                    updatedPrefix++
                }

                add(updatedPrefix)
                updatedPrefix
            }
        }.toIntArray()
    }
}