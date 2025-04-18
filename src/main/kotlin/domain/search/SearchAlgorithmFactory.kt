package domain.search

class SearchAlgorithmFactory(
    private val linearSearchAlgorithm: LinearSearchAlgorithm,
    private val kmpSearchAlgorithm: KMPSearchAlgorithm
){
    enum class SearchType{
        LinearSearchAlgorithm,
        KMPSearchAlgorithm,
    }
    fun createSearchAlgorithm(type: SearchType) : SearchAlgorithm {
        return when(type){
            SearchType.LinearSearchAlgorithm -> linearSearchAlgorithm
            SearchType.KMPSearchAlgorithm -> kmpSearchAlgorithm
        }
    }
}