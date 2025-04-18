package domain.utils

class KMPPatternMatcher : PatternMatcher {
    override fun match(text: String, pattern: String): Boolean {
        if(pattern.isBlank()) return true
        if(text.isBlank()) return false

        val lps = computeLPSArray(pattern)
        var i = 0
        var j = 0
        while (i < text.length) {
            if(pattern[j].equals(text[i], ignoreCase = true)) {
                i++
                j++
            }
            if(j ==pattern.length) {
                return true
            }else if(i<text.length && !pattern[j].equals(text[i], ignoreCase = true)) {
                if(j!=0){
                    j = lps[j-1]
                }else{
                    i++
                }
            }
        }
        return false
    }
    private fun computeLPSArray(pattern: String): IntArray {
        val lps = IntArray(pattern.length)
        var len = 0
        var i = 1
        while (i < pattern.length) {
            if (pattern[i].equals(pattern[len], ignoreCase = true)) {
                len++
                lps[i] = len
                i++
            }else{
                if(len !=0){
                    len = lps[len-1]
                }else{
                    lps[i] = 0
                    i++
                }
            }
        }
        return lps
    }
}