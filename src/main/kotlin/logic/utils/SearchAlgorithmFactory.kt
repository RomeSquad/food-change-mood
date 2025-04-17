package logic.utils

class SearchAlgorithmFactory{
    enum class SearchType{
        LinearSearchAlgorithm,
        KMPSearchAlgorithm,
    }
    fun createSearchAlgorithm(type: SearchType) : SearchAlgorithm {
        return when(type){
            SearchType.LinearSearchAlgorithm -> LinearSearchAlgorithm()
            SearchType.KMPSearchAlgorithm -> KMPSearchAlgorithm()
        }
    }
}