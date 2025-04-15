package data.utils

class CsvParserImpl: CsvParser {
    override fun parseCsv(content: String): List<List<String>> {
        val rows = mutableListOf<List<String>>()
        val currentRow = mutableListOf<String>()
        val currentCell = StringBuilder()
        var withinQuotes = false
        var index = 0

        while(index < content.length) {
            val char = content[index]

            when{
                char == '"' ->{
                    if(index+1 < content.length && content[index+1] == '"'){
                        currentCell.append('"')
                        index++
                    }else{
                        withinQuotes = !withinQuotes
                    }
                }
                char ==',' && !withinQuotes -> {
                    currentRow.add(currentCell.toString())
                    currentCell.clear()
                }
                char== '\n' && !withinQuotes -> {
                    if(currentCell.isNotEmpty() || currentRow.isNotEmpty()){
                        currentRow.add(currentCell.toString())
                        rows.add(currentRow.toList())
                        currentCell.clear()
                        currentRow.clear()
                    }
                }
                else->{
                    currentCell.append(char)
                }
            }
            index++
        }
        if(currentCell.isNotEmpty() && currentRow.isNotEmpty()){
            currentRow.add(currentCell.toString())
            rows.add(currentRow.toList())
        }
        return rows
    }

    override fun parseStringList(list: String): List<String> {
        if(list.isEmpty() ||list =="[]") return listOf()
        val cleanList = list.trim()

        if(cleanList.startsWith("[") && cleanList.endsWith("]")) {
            val data = cleanList.substring(1, cleanList.length - 1) //try remove
            if(data.isEmpty()) return listOf()

            val items = mutableListOf<String>()
            var currentItem = StringBuilder()
            var withinQuotes = false

            for(index in data.indices) {
                val char = data[index]

                when{
                    char == '\'' ->{
                        withinQuotes =!withinQuotes
                    }
                    char == ',' && !withinQuotes -> {
                        items.add(currentItem.toString().trim())
                        currentItem.clear()
                    }
                    else->{
                        currentItem.append(char)
                    }
                }
            }
            if(currentItem.isNotEmpty()){
                items.add(currentItem.toString().trim())
            }
            return items.filter { it.isNotEmpty() }
        }
        return listOf(cleanList)
    }

    override fun parseDoubleList(list: String): List<Double> {
        return parseStringList(list).mapNotNull { it.toDoubleOrNull() }
    }

}