package data.utils

class CsvParserImpl: CsvParser {
    override fun parseCsv(content: String): List<List<String>> {
        if (content.isEmpty()) return emptyList()

        val handledContent = handleMultiLineCells(content)

        return handledContent.split('\n')
            .filter { it.isNotEmpty() }
            .map { parseCsvLine(it) }
    }

    private fun handleMultiLineCells(content: String): String {
        val result = StringBuilder()
        var withinField = false

        for (i in content.indices) {
            val char = content[i]

            if (char == '"') {
                withinField = !withinField
                result.append(char)
                continue
            }

            if ((char == '\n') && withinField) {
                if (i + 1 < content.length && content[i + 1] == '\n') {
                    continue
                }
            } else {
                result.append(char)
            }
        }

        return result.toString()
    }

    private fun parseCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        val fieldBuilder = StringBuilder()
        var withinField = false

        var i = 0
        while (i < line.length) {
            val char = line[i]

            if (char == '"') {
                withinField = !withinField
                fieldBuilder.append(char)
            } else if (char == ',' && !withinField) {
                result.add(fieldBuilder.toString())
                fieldBuilder.clear()
            } else {
                fieldBuilder.append(char)
            }
            i++
        }

        if (fieldBuilder.isNotEmpty()) {
            result.add(fieldBuilder.toString())
        }

        return result
    }



    override fun parseStringList(list: String): List<String> {
        val trimmed = list.trim()

        if (trimmed.isEmpty() || trimmed == "[]") return emptyList()

        if (!trimmed.startsWith("[") || !trimmed.endsWith("]")) {
            return listOf(trimmed)
        }

        val content = trimmed.substring(1, trimmed.length - 1)
        if (content.isEmpty()) return emptyList()

        val result = mutableListOf<String>()
        val itemBuilder = StringBuilder()
        var withinField = false

        for (c in content) {
            when {
                (c == '\'' || c == '"') -> {
                    withinField = !withinField
                    itemBuilder.append(c)
                }
                c == ',' && !withinField -> {
                    result.add(itemBuilder.toString().trim())
                    itemBuilder.clear()
                }
                else -> {
                    itemBuilder.append(c)
                }
            }
        }

        if (itemBuilder.isNotEmpty()) {
            result.add(itemBuilder.toString().trim())
        }

        return result.filter { it.isNotEmpty() }
    }

    override fun parseDoubleList(list: String): List<Double> {
        val cleaned = list.trim()

        if (cleaned.isEmpty() || cleaned == "[]") return emptyList()

        val content = if (cleaned.startsWith("\"[") && cleaned.endsWith("]\"")) {
            cleaned.removeSurrounding("\"[","]\"")
        } else {
            cleaned
        }

        return content.split(",")
            .map { it.trim() }
            .mapNotNull { it.toDoubleOrNull() }
    }
}