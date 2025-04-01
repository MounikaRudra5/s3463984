package uk.ac.tees.mad.quoteverse.screens.favorite

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import uk.ac.tees.mad.quoteverse.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    modifier: Modifier = Modifier) {
    val favoriteQuoteList by viewModel.favoriteQuotes.collectAsState()
    val context = LocalContext.current
    Box(modifier = modifier
        .fillMaxSize()
    ){
        LazyColumn {
            items(favoriteQuoteList){favoriteQuote->
                FavQuoteItem(
                    favoriteQuote = favoriteQuote,
                    onCopyClick = {},
                    onShareClick = {},
                    onDeleteClick = {viewModel.deleteFavoriteQuote(favoriteQuote)
                    }
                )
            }
        }
    }
}