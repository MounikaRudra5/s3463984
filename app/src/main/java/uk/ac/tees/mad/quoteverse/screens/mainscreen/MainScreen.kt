package uk.ac.tees.mad.quoteverse.screens.mainscreen

import android.provider.ContactsContract.Contacts
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
import uk.ac.tees.mad.quoteverse.utils.Constants
import uk.ac.tees.mad.quoteverse.viewmodel.AuthenticationViewModel

@Composable
fun MainScreen(
    viewModel: AuthenticationViewModel,
    navController: NavController,
    modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()) {
        Column {
            Text("Welcome to QuoteVerse")
            Spacer(modifier = Modifier.height(48.dp))
            Button(onClick = {
                viewModel.logOut()
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