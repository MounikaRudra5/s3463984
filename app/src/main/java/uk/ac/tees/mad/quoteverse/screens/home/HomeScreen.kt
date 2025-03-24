package uk.ac.tees.mad.quoteverse.screens.home

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.quoteverse.screens.mainscreen.SearchBar
import uk.ac.tees.mad.quoteverse.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    ) {
    val quotes by viewModel.quotes.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    Box(modifier = modifier
        .fillMaxSize()
    ){
        Column {
            SearchBar(searchQuery, onQueryChange = {searchQuery=it})
            Text("Trending Quotes",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyColumn {
                items(quotes){quote->
                    QuoteItem(
                        quote.q,
                        quote.a,
                        {},
                        {},
                        {}
                    )
                }
            }
        }
    }
}