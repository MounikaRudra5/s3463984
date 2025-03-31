package uk.ac.tees.mad.quoteverse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
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

    private var auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    private val _favoriteQuotes = MutableStateFlow<List<FavoriteQuote>>(emptyList())
    val favoriteQuotes: StateFlow<List<FavoriteQuote>> = _favoriteQuotes

    init {
        auth.currentUser?.let { getFavorites(it.uid) }
    }

    private fun getFavorites(userId: String) {
        viewModelScope.launch {
            repository.getFavoriteQuotes(userId).collect { favorites ->
                _favoriteQuotes.value = favorites
            }
        }
    }

    fun deleteFavoriteQuote(favQuote:FavoriteQuote){
        viewModelScope.launch {
            repository.removeFavoriteQuote(favQuote)
            db.collection(Constants.USER)
                .document(getUserId())
                .collection(Constants.FAV_QUOTES)
                .document(favQuote.id.toString())
                .delete()
        }
    }

    fun getUserId():String{
        return auth.currentUser?.uid ?: ""
    }
}