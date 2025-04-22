package data.utils

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.io.FileNotFoundException

class CsvFileReaderTest {

    private lateinit var file: File
    private lateinit var csvFileReader: CsvFileReader

    @BeforeEach
    fun setUp() {
        file = mockk()
    }

    @Test
    fun `given not a csv file when initializing then exception is thrown`() {
        // given
        every { file.name } returns "meals.pdf"
        every { file.exists() } returns true


        // when & then
        assertThrows<Exception> {
            csvFileReader = CsvFileReader(file)
        }
    }
    @Test
    fun `given a csv file when initializing then no exception is thrown`() {
        // given
        every { file.name } returns "meals.csv"
        every { file.exists() } returns true


        // when & then
        assertDoesNotThrow {
            csvFileReader = CsvFileReader(file)
        }
    }
    @Test
    fun `given not exist file when initializing then exception is thrown`() {
        // given
        every { file.name } returns "meals.csv"
        every { file.exists() } returns false


        // when & then
        assertThrows<FileNotFoundException> {
            csvFileReader = CsvFileReader(file)
        }
    }
    @Test
    fun `given an exist file when initializing then no exception is thrown`() {
        // given
        every { file.name } returns "meals.csv"
        every { file.exists() } returns true

        // when & then
        assertDoesNotThrow {
            csvFileReader = CsvFileReader(file)
        }
    }
    @Test
    fun `given an exist file when read lines then return list of string lines`() {
        // given
        val fakeFile = File("src/test/fake-meals.csv")
        csvFileReader = CsvFileReader(fakeFile)
        // when & then
        assertThat(csvFileReader.readLines().size).isEqualTo(6)
    }
}