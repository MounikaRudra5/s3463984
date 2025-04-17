package uk.ac.tees.mad.quoteverse.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.quoteverse.data.local.FavoriteQuoteRepository
import uk.ac.tees.mad.quoteverse.data.remote.RetrofitInstance
import uk.ac.tees.mad.quoteverse.model.FavoriteQuote
import uk.ac.tees.mad.quoteverse.model.QuoteResponse
import uk.ac.tees.mad.quoteverse.utils.Constants
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: FavoriteQuoteRepository) :ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    private val _quotes = MutableStateFlow(listOf<QuoteResponse>())
    val quotes:StateFlow<List<QuoteResponse>> get() = _quotes

    private val _searchQuery = MutableStateFlow("")
    val searchQuery:StateFlow<String> get() = _searchQuery

    init {
        fetchQuotes()
    }

    fun onSearchQueryChange(query:String){
        _searchQuery.value = query
    }


    private fun fetchQuotes() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getQuotes()
                _quotes.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addFavorite(context: Context,quote: FavoriteQuote) {
        viewModelScope.launch {
            db.collection(Constants.USER)
                .document(getUserId())
                .collection(Constants.FAV_QUOTES)
                .add(quote)
                .addOnSuccessListener { docRef->
                    val documentId = docRef.id
                    val updatedQuote = quote.copy(fireStoreId = documentId)
                    docRef.set(updatedQuote, SetOptions.merge())
                    viewModelScope.launch {
                        repository.addFavoriteQuote(updatedQuote)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context,"Failed to add to favorite, please check network",Toast.LENGTH_SHORT ).show()
                }
        }
    }

    fun getUserId():String{
        return auth.currentUser?.uid ?: ""
    }
}