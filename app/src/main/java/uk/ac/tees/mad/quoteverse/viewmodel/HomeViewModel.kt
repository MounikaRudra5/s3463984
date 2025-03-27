package uk.ac.tees.mad.quoteverse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.quoteverse.data.remote.RetrofitInstance
import uk.ac.tees.mad.quoteverse.model.QuoteResponse

class HomeViewModel :ViewModel() {

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
}