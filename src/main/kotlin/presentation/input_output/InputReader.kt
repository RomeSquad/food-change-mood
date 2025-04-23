package presentation.input_output

interface InputReader {
    fun readString (): String
    fun readIntOrNull () : Int?
    fun readDoubleOrNull () : Double?
}