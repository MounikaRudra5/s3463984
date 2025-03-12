package uk.ac.tees.mad.quoteverse

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.quoteverse.screens.authentication.LogInScreen
import uk.ac.tees.mad.quoteverse.screens.authentication.SignUpScreen
import uk.ac.tees.mad.quoteverse.screens.mainscreen.MainScreen
import uk.ac.tees.mad.quoteverse.screens.splashscreen.SplashScreen
import uk.ac.tees.mad.quoteverse.utils.Constants
import uk.ac.tees.mad.quoteverse.viewmodel.AuthenticationViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MyApp(application: Application) {
    val navController = rememberNavController()
    val authenticationViewModel = AuthenticationViewModel(application)

    NavHost(navController = navController, startDestination = Constants.SPLASHSCREEN) {
        composable(Constants.SPLASHSCREEN) {
            SplashScreen(authenticationViewModel,navController = navController)
        }

        composable(Constants.MAINSCREEN) {
            MainScreen(authenticationViewModel, navController)
        }

        composable(Constants.LOGINSCREEN){
            LogInScreen(authenticationViewModel,navController = navController)
        }

        composable(Constants.SIGNUPSCREEN){
            SignUpScreen(authenticationViewModel,navController = navController)
        }
    }
}