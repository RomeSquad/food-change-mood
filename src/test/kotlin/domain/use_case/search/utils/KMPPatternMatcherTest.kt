package domain.use_case.search.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KMPPatternMatcherTest {
    private lateinit var patternMatcher: KMPPatternMatcher

    @BeforeEach
    fun init() {
        patternMatcher = KMPPatternMatcher()
    }

    @Test
    fun `given text and matched pattern should return true`() {
        val pattern = "pizza"
        val text = "pizza"

        val result = patternMatcher.match(text, pattern)

        assertTrue(result)
    }
    @Test
    fun `given text and un matched pattern should return true`() {
        val pattern = "watermelon"
        val text = "pizza"

        val result = patternMatcher.match(text, pattern)

        assertFalse(result)
    }
    @Test
    fun `given empty text and  valid pattern should return false`() {
        val pattern = "watermelon"
        val text = ""

        val result = patternMatcher.match(text, pattern)

        assertFalse(result)
    }
    @Test
    fun `given valid text and empty pattern should return true`() {
        val pattern = ""
        val text = "watermelon"

        val result = patternMatcher.match(text, pattern)

        assertTrue(result)
    }
    @Test
    fun `given both text and pattern empty should return true`() {
        val pattern = ""
        val text = ""

        val result = patternMatcher.match(text, pattern)

        assertTrue(result)
    }

}