package uk.ac.tees.mad.quoteverse.model

data class FavoriteQuote(
    val id:Int = 0,
    val fireStoreId :String = "",
    val userId: String = "",
    val quote: String = "",
    val author: String = "",
    val date:Long = 0
)
