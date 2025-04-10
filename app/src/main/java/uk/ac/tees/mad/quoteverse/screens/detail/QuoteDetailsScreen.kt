package uk.ac.tees.mad.quoteverse.screens.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.quoteverse.viewmodel.HomeViewModel

@Composable
fun QuoteDetailsScreen(
    startIndex: Int
) {
    val viewModel:HomeViewModel = hiltViewModel()
    val quotes by viewModel.quotes.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        initialPageOffsetFraction = 0f,
        pageCount = {quotes.size}
    )

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        QuoteItemScreen(quote = quotes[page].q,
            author = quotes[page].a,
            {},
            {},
            {}
            )
    }
}
