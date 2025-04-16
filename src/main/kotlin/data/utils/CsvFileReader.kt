package data.utils

import java.io.File
import java.io.FileNotFoundException

class CsvFileReader(
    private val file: File,
){
    init {
        if (!file.exists()) {
            throw FileNotFoundException("File does not exist")
        }
    }
    fun readLines(): List<String> {
        return file.readLines()
    }
}