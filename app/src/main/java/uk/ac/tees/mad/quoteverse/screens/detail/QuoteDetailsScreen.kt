package uk.ac.tees.mad.quoteverse.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import uk.ac.tees.mad.quoteverse.viewmodel.HomeViewModel

@Composable
fun QuoteDetailsScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    startIndex: Int
) {
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
        QuoteItemScreen(quote = quotes[page].q, author = quotes[page].a)
    }
}
