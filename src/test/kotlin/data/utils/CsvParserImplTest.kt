package data.utils

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CsvParserImplTest {
    private lateinit var csvParser: CsvParserImpl

    @BeforeEach
    fun setUp() {
        csvParser = CsvParserImpl()
    }

    @Test
    fun `given empty content when parsing csv then return empty list`() {
        // given
        val content = ""

        // when
        val result = csvParser.parseCsv(content)

        // then
        assertThat(result).isEmpty()
    }

    @Test
    fun `given single line csv when parsing then return list with one row`() {
        // given
        val content ="a bit different  breakfast pizza\t31490\t30\t26278\t6/17/2002\t\"['30-minutes-or-less', 'time-to-make', 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'breakfast', 'main-dish', 'pork', 'american', 'oven', 'easy', 'kid-friendly', 'pizza', 'dietary', 'northeastern-united-states', 'meat', 'equipment']\"\t\"[173.4, 18.0, 0.0, 17.0, 22.0, 35.0, 1.0]\"\t9\t\"['preheat oven to 425 degrees f', 'press dough into the bottom and sides of a 12 inch pizza pan', 'bake for 5 minutes until set but not browned', 'cut sausage into small pieces', 'whisk eggs and milk in a bowl until frothy', 'spoon sausage over baked crust and sprinkle with cheese', 'pour egg mixture slowly over sausage and cheese', 's& p to taste', 'bake 15-20 minutes or until eggs are set and crust is brown']\"\tthis recipe calls for the crust to be prebaked a bit before adding ingredients. feel free to change sausage to ham or bacon. this warms well in the microwave for those late risers.\t\"['prepared pizza crust', 'sausage patty', 'eggs', 'milk', 'salt and pepper', 'cheese']\"\t6\n"
        // when
        val result = csvParser.parseCsv(content)

        // then
        assertThat(result).hasSize(1)
    }
    @Test
    fun `given csv with  multiline row content when parsing then return correctly formatted row`() {
        // given
        val content =
            "arriba   baked winter squash mexican style\t137739\t55\t47892\t9/16/2005\t\"['60-minutes-or-less', 'time-to-make', 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'side-dishes', 'vegetables', 'mexican', 'easy', 'fall', 'holiday-event', 'vegetarian', 'winter', 'dietary', 'christmas', 'seasonal', 'squash']\"\t\"[51.5, 0.0, 13.0, 0.0, 2.0, 0.0, 4.0]\"\t11\t\"['make a choice and proceed with recipe', 'depending on size of squash , cut into half or fourths', 'remove seeds', 'for spicy squash , drizzle olive oil or melted butter over each cut squash piece', 'season with mexican seasoning mix ii', 'for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece', 'season with sweet mexican spice mix', 'bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin', 'be careful not to burn the squash especially if you opt to use sugar or butter', 'if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking', 'if desired , season with salt']\"\t\"autumn is my favorite time of year to cook! this recipe \n" +
                    "can be prepared either spicy or sweet, your choice!\n" +
                    "two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.\"\t\"['winter squash', 'mexican seasoning', 'mixed spice', 'honey', 'butter', 'olive oil', 'salt']\"\t7"

        // when
        val result = csvParser.parseCsv(content)

        // then
        assertThat(result).hasSize(1)
    }
    @Test
    fun `given csv with blank lines when parsing then skip blank lines`() {
        // given
        val content =
            "arriba   baked winter squash mexican style\t137739\t55\t47892\t9/16/2005\t\"['60-minutes-or-less', 'time-to-make', 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'side-dishes', 'vegetables', 'mexican', 'easy', 'fall', 'holiday-event', 'vegetarian', 'winter', 'dietary', 'christmas', 'seasonal', 'squash']\"\t\"[51.5, 0.0, 13.0, 0.0, 2.0, 0.0, 4.0]\"\t11\t\"['make a choice and proceed with recipe', 'depending on size of squash , cut into half or fourths', 'remove seeds', 'for spicy squash , drizzle olive oil or melted butter over each cut squash piece', 'season with mexican seasoning mix ii', 'for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece', 'season with sweet mexican spice mix', 'bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin', 'be careful not to burn the squash especially if you opt to use sugar or butter', 'if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking', 'if desired , season with salt']\"\t\"autumn is my favorite time of year to cook! this recipe \n" +
                    "can be prepared either spicy or sweet, your choice!\n\n" +
                    "two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.\"\t\"['winter squash', 'mexican seasoning', 'mixed spice', 'honey', 'butter', 'olive oil', 'salt']\"\t7"

        // when
        val result = csvParser.parseCsv(content)

        // then
        assertThat(result).hasSize(1)
    }
    @Test
    fun `given csv line with quoted commas when parsing then preserve commas in quotes`() {
        // given
        val content = "name,description\n\"ahmed, Jr\",engineer"

        // when
        val result = csvParser.parseCsv(content)

        // then
        assertThat(result).hasSize(2)
        assertThat(result[0]).containsExactly("name", "description")
        assertThat(result[1]).containsExactly("\"ahmed, Jr\"", "engineer")
    }
    @Test
    fun `given csv line with quoted commas and extra comma in the end when parsing then preserve commas in quotes`() {
        // given
        val content = "name,description\n\"ahmed, Jr\",engineer,"

        // when
        val result = csvParser.parseCsv(content)

        // then
        assertThat(result).hasSize(2)
        assertThat(result[0]).containsExactly("name", "description")
        assertThat(result[1]).containsExactly("\"ahmed, Jr\"", "engineer")
    }
    @Test
    fun `given valid string list from csv when parsing then return list of strings`() {
        // given
        val input = "\"['winter squash', 'mexican seasoning', 'mixed spice', 'honey', 'butter', 'olive oil', 'salt']\""

        // when
        val result = csvParser.parseStringList(input)

        // then
        assertThat(result).containsExactly(
            "winter squash", "mexican seasoning", "mixed spice", "honey", "butter", "olive oil", "salt"
        )
    }
    @Test
    fun `given string list with empty elements when parsing then filter out empty elements`() {
        // given
        val input = "\"['apple', '', 'banana']\""

        // when
        val result = csvParser.parseStringList(input)

        // then
        assertThat(result).containsExactly("apple", "banana")
    }
    @Test
    fun `given empty string list when parsing then return empty list`() {
        // given
        val input = "\"[]\""

        // when
        val result = csvParser.parseStringList(input)

        // then
        assertThat(result).isEmpty()
    }
    @Test
    fun `given valid nutrition list from csv when parsing then return list of doubles`() {
        // given
        val input = "\"[51.5, 0.0, 13.0, 0.0, 2.0, 0.0, 4.0]\""

        // when
        val result = csvParser.parseDoubleList(input)

        // then
        assertThat(result).containsExactly(51.5, 0.0, 13.0, 0.0, 2.0, 0.0, 4.0)
    }
    @Test
    fun `given double list with invalid numbers when parsing then filter out invalid entries`() {
        // given
        val input = "[1.0, invalid, 3.7]"

        // when
        val result = csvParser.parseDoubleList(input)

        // then
        assertThat(result).containsExactly(1.0, 3.7)
    }
    @Test
    fun `given empty double list when parsing then return empty list`() {
        // given
        val input = "[]"

        // when
        val result = csvParser.parseDoubleList(input)

        // then
        assertThat(result).isEmpty()
    }
}