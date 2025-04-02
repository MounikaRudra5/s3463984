package uk.ac.tees.mad.quoteverse.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.ac.tees.mad.quoteverse.model.FavoriteQuote

class FavoriteQuoteRepositoryImpl(private val dao:FavoriteQuoteDao):FavoriteQuoteRepository {
    override suspend fun addFavoriteQuote(quote: FavoriteQuote) {
        dao.addFavoriteQuote(
            FavoriteQuoteEntity(
                fireStoreId = quote.fireStoreId,
                userId = quote.userId,
                quote = quote.quote,
                author = quote.author,
                date = quote.date
            )
        )
    }

    override fun getFavoriteQuotes(userId: String): Flow<List<FavoriteQuote>> {
        return dao.getFavoriteQuotes(userId).map { list ->
            list.map { entity ->
                FavoriteQuote(
                    fireStoreId = entity.fireStoreId,
                    id = entity.id,
                    userId = entity.userId,
                    quote = entity.quote,
                    author = entity.author,
                    date = entity.date
                )
            }
        }
    }

    override suspend fun removeFavoriteQuote(quote: FavoriteQuote) {
        dao.removeFavoriteQuote(
            FavoriteQuoteEntity(
                fireStoreId = quote.fireStoreId,
                id = quote.id,
                userId = quote.userId,
                quote = quote.quote,
                author = quote.author,
                date = quote.date
            )
        )
    }
}