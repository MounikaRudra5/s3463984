package uk.ac.tees.mad.quoteverse.screens.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.quoteverse.screens.home.QuoteItem
import uk.ac.tees.mad.quoteverse.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    modifier: Modifier = Modifier) {
    val favoriteQuoteList by viewModel.favoriteQuotes.collectAsState()
    Box(modifier = modifier
        .fillMaxSize()
    ){
        LazyColumn {
            items(favoriteQuoteList){favoriteQuote->
                QuoteItem(
                    quote = favoriteQuote.quote,
                    author = favoriteQuote.author,
                    onCopyClick = {},
                    onFavoriteClick = { _, _ ->},
                    onShareClick = {},
                )
            }
        }
    }
}