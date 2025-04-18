package domain.utils

class SearchAlgorithmFactory{
    enum class SearchType{
        LinearSearchAlgorithm,
        KMPSearchAlgorithm,
    }
    fun createSearchAlgorithm(type: SearchType = SearchType.KMPSearchAlgorithm) : SearchAlgorithm {
        return when(type){
            SearchType.LinearSearchAlgorithm -> LinearSearchAlgorithm()
            SearchType.KMPSearchAlgorithm -> KMPSearchAlgorithm()
        }
    }
}