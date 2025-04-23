package domain.use_case.search.utils

class KMPPatternMatcher : PatternMatcher {
    override fun match(text: String, pattern: String): Boolean {
        if(pattern.isBlank()) return true
        if(text.isBlank()) return false

        val resultArray = computeLPSArray(pattern)
        var textIndex = 0
        var patternIndex = 0
        while (textIndex < text.length) {
            if(pattern[patternIndex].equals(text[textIndex], ignoreCase = true)) {
                textIndex++
                patternIndex++
            }
            if(patternIndex ==pattern.length) {
                return true
            }else if(textIndex<text.length && !pattern[patternIndex].equals(text[textIndex], ignoreCase = true)) {
                if(patternIndex!=0){
                    patternIndex = resultArray[patternIndex-1]
                }else{
                    textIndex++
                }
            }
        }
        return false
    }
    private fun computeLPSArray(pattern: String): IntArray {
        val resultArray = IntArray(pattern.length)
        var prefixLength = 0
        var index = 1
        while (index < pattern.length) {
            if (pattern[index].equals(pattern[prefixLength], ignoreCase = true)) {
                prefixLength++
                resultArray[index] = prefixLength
                index++
            }else{
                if(prefixLength !=0){
                    prefixLength = resultArray[prefixLength-1]
                }else{
                    resultArray[index] = 0
                    index++
                }
            }
        }
        return resultArray
    }
}