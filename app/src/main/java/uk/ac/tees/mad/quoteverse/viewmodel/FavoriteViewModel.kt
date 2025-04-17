package uk.ac.tees.mad.quoteverse.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.quoteverse.data.local.FavoriteQuoteRepository
import uk.ac.tees.mad.quoteverse.model.FavoriteQuote
import uk.ac.tees.mad.quoteverse.utils.Constants
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteQuoteRepository
):ViewModel() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db :FirebaseFirestore

    private val _favoriteQuotes = MutableStateFlow<List<FavoriteQuote>>(emptyList())
    val favoriteQuotes: StateFlow<List<FavoriteQuote>> = _favoriteQuotes

    init {
        auth = Firebase.auth
        db = Firebase.firestore
        auth.currentUser?.let { getFavorites(it.uid) }
    }

    private fun getFavorites(userId: String) {
        viewModelScope.launch {
            repository.getFavoriteQuotes(userId).collect { favorites ->
                if(favorites.isNotEmpty()){
                    _favoriteQuotes.value = favorites
                }
                else{
                    fetchFromFirestore(userId)
                }
            }
        }
    }

    private fun fetchFromFirestore(userId:String){
        db.collection(Constants.USER)
            .document(userId)
            .collection(Constants.FAV_QUOTES)
            .get()
            .addOnSuccessListener { documents->
                val quotes = documents.mapNotNull { doc->
                    doc.toObject(FavoriteQuote::class.java)
                }

                viewModelScope.launch {
                    quotes.forEach {
                        repository.addFavoriteQuote(it)
                    }
                    _favoriteQuotes.value = quotes
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching quotes", e)
            }
    }

    fun deleteFavoriteQuote(context: Context,favQuote:FavoriteQuote){
        viewModelScope.launch {
            val docRef = db.collection(Constants.USER)
                .document(getUserId())
                .collection(Constants.FAV_QUOTES)
                .document(favQuote.fireStoreId)

                docRef.delete().addOnSuccessListener {
                    viewModelScope.launch {
                        repository.removeFavoriteQuote(favQuote)
                    }
                }
                    .addOnFailureListener { e ->
                        Toast.makeText(context,"Failed to delete, please check network",
                            Toast.LENGTH_SHORT ).show()
                        Log.e("Firestore", "Error deleting document", e)
                    }
        }
    }

    private fun getUserId():String{
        return auth.currentUser?.uid ?: ""
    }
}