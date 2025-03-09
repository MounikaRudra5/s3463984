package uk.ac.tees.mad.quoteverse.screens.authentication

import android.provider.CalendarContract.Colors
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.quoteverse.screens.authentication.components.TextInputGroup
import uk.ac.tees.mad.quoteverse.screens.authentication.components.TextInputGroupPassword
import uk.ac.tees.mad.quoteverse.ui.theme.QuoteVerseTheme

@Composable
fun LogInScreen(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Spacer(modifier = Modifier.height(28.dp))
            Text("Sign In",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
                )

            TextInputGroup(
                headingText = "Email",
                value = email,
                label = "Your Email",
                onValueChange = {email = it},
            )

            TextInputGroupPassword(
                value = password,
                onValueChange = {password = it}
            )

            Spacer(modifier = Modifier.width(20.dp))

            TextButton(onClick = {
                Toast.makeText(context,"Clicked Login Button", Toast.LENGTH_SHORT).show()
            },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
                ) {
                Text("Sing In")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()) {
                Text("Don't have an account?")
                Text(" Sign in",
                    color = MaterialTheme.colorScheme.primary
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LogInScreenPrev() {
    QuoteVerseTheme {
        LogInScreen()
    }
}