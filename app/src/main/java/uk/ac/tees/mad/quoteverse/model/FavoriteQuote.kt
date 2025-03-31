package uk.ac.tees.mad.quoteverse.model

data class FavoriteQuote(
    val id:Int = 0,
    val userId: String,
    val quote: String,
    val author: String
)
