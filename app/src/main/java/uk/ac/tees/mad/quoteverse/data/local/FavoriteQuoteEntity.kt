package uk.ac.tees.mad.quoteverse.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_quotes")
data class FavoriteQuoteEntity(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val fireStoreId:String,
    val userId:String,
    val quote:String,
    val author:String,
    val date:Long
)