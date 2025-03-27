package uk.ac.tees.mad.quoteverse.screens.authentication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.quoteverse.screens.authentication.components.TextInputGroup
import uk.ac.tees.mad.quoteverse.screens.authentication.components.TextInputGroupPassword
import uk.ac.tees.mad.quoteverse.utils.Constants
import uk.ac.tees.mad.quoteverse.viewmodel.AuthenticationViewModel

@Composable
fun SignUpScreen(
    viewModel: AuthenticationViewModel,
    navController: NavController,
    modifier: Modifier = Modifier) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(loginSuccess) {
        if (loginSuccess){
            Toast.makeText(context, "New user created", Toast.LENGTH_SHORT).show()
            viewModel.onChangeISLoginSuccess()
            navController.navigate(Constants.MAINSCREEN){
                popUpTo(Constants.SIGNUPSCREEN){
                    inclusive = true
                }
            }
        }
    }
    Box(
        modifier = modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Spacer(modifier = Modifier.height(48.dp))
            Text("Create Account",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 36.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(22.dp))

            TextInputGroup(
                headingText = "Name",
                value = name,
                label = "Your name",
                onValueChange = {viewModel.onNameChange(it)},
            )

            TextInputGroup(
                headingText = "Email",
                value = email,
                label = "Your email",
                onValueChange = {viewModel.onEmailChange(it)},
            )

            TextInputGroupPassword(
                value = password,
                onValueChange = {viewModel.onPasswordChange(it)}
            )

            Spacer(modifier = Modifier.height(20.dp))
            if (isLoading){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
            else{
                TextButton(onClick = {
                    if (viewModel.validateSignupForm(context)) {
                        viewModel.createNewUser()
                    }
                },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Sign Up")
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()) {
                    Text("Already have an account?",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(" Log in",
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
                }
            }

        }
    }
}
