package uk.ac.tees.mad.quoteverse

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.quoteverse.screens.authentication.LogInScreen
import uk.ac.tees.mad.quoteverse.screens.authentication.SignUpScreen
import uk.ac.tees.mad.quoteverse.screens.mainscreen.MainScreen
import uk.ac.tees.mad.quoteverse.screens.splashscreen.SplashScreen
import uk.ac.tees.mad.quoteverse.utils.Constants
import uk.ac.tees.mad.quoteverse.viewmodel.AuthenticationViewModel
import uk.ac.tees.mad.quoteverse.viewmodel.HomeViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val authenticationViewModel:AuthenticationViewModel = viewModel()
    val homeViewModel:HomeViewModel = viewModel()

    NavHost(navController = navController, startDestination = Constants.SPLASHSCREEN) {
        composable(Constants.SPLASHSCREEN) {
            SplashScreen(authenticationViewModel,navController = navController)
        }

        composable(Constants.MAINSCREEN) {
            MainScreen(homeViewModel)
        }

        composable(Constants.LOGINSCREEN){
            LogInScreen(authenticationViewModel,navController = navController)
        }

        composable(Constants.SIGNUPSCREEN){
            SignUpScreen(authenticationViewModel,navController = navController)
        }
    }
}