package presentation.input_output

class ConsoleInputReader : InputReader {
    override fun readString(): String = readlnOrNull()?:throw IllegalArgumentException("Input cannot be null")


    override fun readIntOrNull(): Int? = readlnOrNull()?.toIntOrNull()

    override fun readDoubleOrNull(): Double? = readlnOrNull()?.toDoubleOrNull()
}