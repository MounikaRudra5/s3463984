package uk.ac.tees.mad.quoteverse.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import uk.ac.tees.mad.quoteverse.utils.Constants

@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier) {
    val auth: FirebaseAuth = Firebase.auth
    Box(
        modifier = modifier
        .fillMaxSize()
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Settings Screen")
            Spacer(Modifier.height(12.dp))
            Button(onClick = {
                auth.signOut()
                navController.navigate(Constants.LOGINSCREEN){
                    popUpTo(Constants.MAINSCREEN){
                        inclusive = true
                    }
                }
            }) {
                Text("Log out")
            }
        }
    }
}