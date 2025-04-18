package data.utils

class CsvParserImpl : CsvParser {
    override fun parseCsv(content: String): List<List<String>> {
        if (content.isEmpty()) return emptyList()

        val formatedContent = reformatMultiLineCells(content)

        return formatedContent.lines()
            .filter(String::isNotBlank)
            .map (::parseCsvLine)
    }

    private fun reformatMultiLineCells(content: String): String {
        val result = StringBuilder()
        var insideQuotes = false

        for (char in content) {
            when (char) {
                '"' -> {
                    insideQuotes = !insideQuotes
                    result.append(char)
                }
                '\n' -> {
                    if (insideQuotes) result.append(" ") else result.append('\n')
                }
                else -> result.append(char)
            }
        }

        return result.toString()
    }

    private fun parseCsvLine(line: String): List<String> {
        val fields = mutableListOf<String>()
        val currentField = StringBuilder()
        var insideQuotes = false

        for (char in line) {
            when {
                char == '"' -> {
                    insideQuotes = !insideQuotes
                    currentField.append(char)
                }

                char == ',' && !insideQuotes -> {
                    fields.add(currentField.toString())
                    currentField.clear()
                }

                else -> currentField.append(char)
            }
        }

        if (currentField.isNotEmpty()) {
            fields.add(currentField.toString())
        }

        return fields
    }


    override fun parseStringList(list: String): List<String> {
        val listTrim = list.trim()
            .removePrefix("[")
            .removeSuffix("]")

        if (listTrim.isEmpty()) return emptyList()

        return listTrim.split(',')
            .map { it.trim().removeSurrounding("\"") }
            .filter { it.isNotEmpty() }    }

    override fun parseDoubleList(list: String): List<Double> {
        val listTrim = list.trim()
            .removeSurrounding("\"[", "]\"")
            .removeSurrounding("[", "]")

        if (listTrim.isBlank()) return emptyList()

        return listTrim.split(',').mapNotNull { it.trim().toDoubleOrNull() }
    }
}