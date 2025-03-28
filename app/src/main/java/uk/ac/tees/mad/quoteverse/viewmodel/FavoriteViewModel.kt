package uk.ac.tees.mad.quoteverse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.quoteverse.data.local.FavoriteQuoteRepository
import uk.ac.tees.mad.quoteverse.model.FavoriteQuote
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteQuoteRepository
):ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

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
}