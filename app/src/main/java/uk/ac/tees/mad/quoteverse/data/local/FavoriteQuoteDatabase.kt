package uk.ac.tees.mad.quoteverse.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteQuoteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteQuoteDatabase: RoomDatabase() {

    abstract fun favoriteQuoteDao():FavoriteQuoteDao

    companion object{
        @Volatile
        private var INSTANCE :FavoriteQuoteDatabase? = null

        fun getDatabase(context: Context):FavoriteQuoteDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteQuoteDatabase::class.java,
                    "favorite_quotes_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}