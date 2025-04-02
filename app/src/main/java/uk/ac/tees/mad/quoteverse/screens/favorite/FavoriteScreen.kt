package uk.ac.tees.mad.quoteverse.screens.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.quoteverse.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    modifier: Modifier = Modifier) {
    val favoriteQuoteList by viewModel.favoriteQuotes.collectAsState()
    var quoteList by rememberSaveable { mutableStateOf(favoriteQuoteList) }
    var sortOption by remember { mutableStateOf("None") }
    val context = LocalContext.current
    Column(modifier = modifier.fillMaxSize()) {
        Text("Favorite Quotes",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        SortingDropdown(
            sortOption = sortOption,
            onSortChange = { sort ->
                sortOption = sort
                quoteList = when (sortOption) {
                    "Author Name" -> favoriteQuoteList.sortedBy { it.author }
                    "Date Added" -> favoriteQuoteList.sortedBy { it.date }
                    else -> favoriteQuoteList
                }
            },
            onReverseList = { quoteList = quoteList.reversed() }
        )
        LazyColumn {
            items(quoteList){favoriteQuote->
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