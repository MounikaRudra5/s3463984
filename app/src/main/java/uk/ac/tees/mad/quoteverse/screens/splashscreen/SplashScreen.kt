package uk.ac.tees.mad.quoteverse.screens.splashscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import uk.ac.tees.mad.quoteverse.utils.Constants

@Composable
fun SplashScreen(navController: NavController) {
    val fadeInDuration = 1500
    var fadeInText by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        fadeInText = true
        delay(fadeInDuration.toLong())
        navController.navigate(Constants.MAINSCREEN){
            popUpTo(Constants.SPLASHSCREEN){
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = fadeInText,
                enter = fadeIn(animationSpec = tween(fadeInDuration))
            ) {
                Text(
                    text = "QuoteVerse",
                    style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = fadeInText,
                enter = fadeIn(animationSpec = tween(fadeInDuration + 500))
            ) {
                Text(
                    text = "One quote can change your day!",
                    style = TextStyle(fontSize = 18.sp, fontStyle = FontStyle.Italic)
                )
            }
        }
    }
}
