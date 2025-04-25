package data.utils

import domain.InvalidFileExtensionException
import java.io.File
import java.io.FileNotFoundException

class CsvFileReader(
    private val file: File,
) {
    init {
        validateFile(this.file)
    }

    fun validateFile(file: File) {
        if (!file.name.endsWith(".csv")) {
            throw InvalidFileExtensionException("File does not end with csv file")
        }
        if (!file.exists()) {
            throw FileNotFoundException("File does not exist")
        }
    }

    fun readLines(): List<String> {
        return file.readLines()
    }
}