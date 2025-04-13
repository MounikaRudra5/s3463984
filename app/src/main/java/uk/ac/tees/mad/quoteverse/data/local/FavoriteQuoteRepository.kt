package uk.ac.tees.mad.quoteverse.data.local

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.quoteverse.model.FavoriteQuote

interface FavoriteQuoteRepository {
    suspend fun addFavoriteQuote(quote: FavoriteQuote)
    fun getFavoriteQuotes(userId: String): Flow<List<FavoriteQuote>>
    suspend fun removeFavoriteQuote(quote: FavoriteQuote)
    suspend fun deleteAllQuotesByUser(userId: String)
}