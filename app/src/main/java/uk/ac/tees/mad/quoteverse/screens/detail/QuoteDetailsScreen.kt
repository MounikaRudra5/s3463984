package uk.ac.tees.mad.quoteverse.screens.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import uk.ac.tees.mad.quoteverse.model.FavoriteQuote
import uk.ac.tees.mad.quoteverse.utils.Utils
import uk.ac.tees.mad.quoteverse.viewmodel.HomeViewModel

@Composable
fun QuoteDetailsScreen(
    viewModel: HomeViewModel,
    startIndex: Int
) {
    val quotes by viewModel.quotes.collectAsState()
    val context = LocalContext.current
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
            {Utils.copyToClipboard(context, it)},
            {viewModel.addFavorite(context,
                FavoriteQuote(
                    userId = viewModel.getUserId(),
                    quote = quotes[page].q,
                    author = quotes[page].a,
                    date = System.currentTimeMillis()
                )
            )},
            {Utils.shareText(context,it)}
            )
    }
}
