package data.utils

interface CsvParser{
    fun parseCsv(content: String): List<List<String>>
    fun parseStringList(list:String): List<String>
    fun parseDoubleList(list:String): List<Double>
}