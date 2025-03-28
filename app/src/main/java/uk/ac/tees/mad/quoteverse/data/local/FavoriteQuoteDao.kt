package uk.ac.tees.mad.quoteverse.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteQuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteQuote(quote:FavoriteQuoteEntity)

    @Query("SELECT * FROM favorite_quotes WHERE userId = :userId")
    fun getFavoriteQuotes(userId:String): Flow<List<FavoriteQuoteEntity>>

    @Delete
    suspend fun removeFavoriteQuote(quote: FavoriteQuoteEntity)
}