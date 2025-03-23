package uk.ac.tees.mad.quoteverse.screens.mainscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.tees.mad.quoteverse.screens.favorite.FavoriteScreen
import uk.ac.tees.mad.quoteverse.screens.home.HomeScreen
import uk.ac.tees.mad.quoteverse.screens.settings.SettingsScreen
import uk.ac.tees.mad.quoteverse.ui.theme.QuoteVerseTheme

@Composable
fun MainScreen(
    ) {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = { MyBottomNavigationBar(selectedItem, onClick = {selectedItem=it}) }
    ) { padding->

        when(selectedItem){
            0-> HomeScreen(modifier = Modifier.padding(padding))
            1-> FavoriteScreen(modifier = Modifier.padding(padding))
            2 -> SettingsScreen(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPrev() {
    QuoteVerseTheme {
        MainScreen()
    }
}