package uk.ac.tees.mad.quoteverse.screens.mainscreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyBottomNavigationBar(
    isSelected:Int,
    onClick:(Int)->Unit,
    modifier: Modifier = Modifier) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            icon = {
                Icon(imageVector = Icons.Default.Home,
                    contentDescription = "home_icon"
                )
            },
            label = {
                Text("Home")
            },
            selected = isSelected==0,
            onClick = {onClick(0)},
            alwaysShowLabel = false
        )
        NavigationBarItem(
            icon = {
                Icon(imageVector = Icons.Default.Favorite,
                    contentDescription = "home_icon"
                )
            },
            label = {
                Text("Favorite")
            },
            selected = isSelected==1,
            onClick = {onClick(1)},
            alwaysShowLabel = false
        )
        NavigationBarItem(
            icon = {
                Icon(imageVector = Icons.Default.Settings,
                    contentDescription = "home_icon"
                )
            },
            label = {
                Text("Settings")
            },
            selected = isSelected==2,
            onClick = {onClick(2)},
            alwaysShowLabel = false
        )
    }
}