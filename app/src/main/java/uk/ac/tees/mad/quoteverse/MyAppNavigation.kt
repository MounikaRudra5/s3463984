package uk.ac.tees.mad.quoteverse

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.tees.mad.quoteverse.screens.authentication.LogInScreen
import uk.ac.tees.mad.quoteverse.screens.authentication.SignUpScreen
import uk.ac.tees.mad.quoteverse.screens.detail.QuoteDetailsScreen
import uk.ac.tees.mad.quoteverse.screens.mainscreen.MainScreen
import uk.ac.tees.mad.quoteverse.screens.splashscreen.SplashScreen
import uk.ac.tees.mad.quoteverse.utils.Constants
import uk.ac.tees.mad.quoteverse.viewmodel.AuthenticationViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()
    val authenticationViewModel:AuthenticationViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Constants.SPLASHSCREEN) {
        composable(Constants.SPLASHSCREEN) {
            SplashScreen(authenticationViewModel,navController = navController)
        }

        composable(Constants.MAINSCREEN) {
            MainScreen(navController)
        }

        composable(Constants.LOGINSCREEN){
            LogInScreen(authenticationViewModel,navController = navController)
        }

        composable(Constants.SIGNUPSCREEN){
            SignUpScreen(authenticationViewModel,navController = navController)
        }

        composable(
            "${Constants.QUOTEDETAILSCREEN}/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        )
        { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            QuoteDetailsScreen(index)
        }
    }
}
