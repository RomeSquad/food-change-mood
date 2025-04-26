package domain.use_case.search.utils

import data.model.Meal
import domain.use_case.createFakeMealData
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class KMPSearchAlgorithmTest {

    private lateinit var patternMatcher: PatternMatcher
    private lateinit var kmpSearchAlgorithm: KMPSearchAlgorithm
    val data =  listOf(
        createFakeMealData( mealName =  "pizza" ),
        createFakeMealData( mealName =  "Pizza" ),
        createFakeMealData( mealName =  "Potato" )
    )
    @BeforeEach
    fun initialize() {
        patternMatcher = mockk()
        kmpSearchAlgorithm = KMPSearchAlgorithm(patternMatcher)
    }

    @Test
    fun `given valid search query and list of meals contains the given query should return a meals that contain the query `() {
        val query = "Pizza"
        val data = data
        every { patternMatcher.match(data[0].name,"Pizza") } returns true
        every { patternMatcher.match(data[1].name,"Pizza") } returns true
        every { patternMatcher.match(data[2].name,"Pizza") } returns false
        val result = kmpSearchAlgorithm.search(data,query).getOrNull()

        val expected = listOf(data[0],data[1])

        assertEquals(expected, result)
    }
    @Test
    fun `given invalid search query and list of meals contains the given query should return Result of failure `() {
        val query = ""
        val data = data
        assertThrows<Exception> {
            kmpSearchAlgorithm.search(data, query).getOrThrow()
        }
    }
    @Test
    fun `given valid search query and empty list of meals should return Result of failure `() {
        val query = "pizza"
        val data = emptyList<Meal>()
        assertThrows<Exception> {
            kmpSearchAlgorithm.search(data, query).getOrThrow()
        }
    }
}
