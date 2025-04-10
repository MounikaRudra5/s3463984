package uk.ac.tees.mad.quoteverse.screens.mainscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uk.ac.tees.mad.quoteverse.data.local.FavoriteQuoteRepository
import uk.ac.tees.mad.quoteverse.screens.favorite.FavoriteScreen
import uk.ac.tees.mad.quoteverse.screens.home.HomeScreen
import uk.ac.tees.mad.quoteverse.screens.settings.SettingsScreen
import uk.ac.tees.mad.quoteverse.viewmodel.FavoriteViewModel
import uk.ac.tees.mad.quoteverse.viewmodel.HomeViewModel
import uk.ac.tees.mad.quoteverse.viewmodel.SettingsViewModel

@Composable
fun MainScreen(
    navController: NavController
) {
    val homeViewModel:HomeViewModel = hiltViewModel()
    val favoriteViewModel:FavoriteViewModel = hiltViewModel()
    val settingsViewModel:SettingsViewModel = hiltViewModel()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = { MyBottomNavigationBar(selectedItem, onClick = {selectedItem=it}) }
    ) { padding->

        when(selectedItem){
            0-> HomeScreen(homeViewModel, navController, modifier = Modifier.padding(padding))
            1-> FavoriteScreen(favoriteViewModel,modifier = Modifier.padding(padding))
            2 -> SettingsScreen(settingsViewModel,navController,modifier = Modifier.padding(padding))
        }
    }
}