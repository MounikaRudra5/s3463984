package uk.ac.tees.mad.quoteverse.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResetPasswordDialog(
    isEmailSent: Boolean,
    onResetPassword: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var email by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Reset Password") },
        text = {
            if (!isEmailSent) {
                Column {
                    Text("Enter your email to receive a password reset link.")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") }
                    )
                }
            } else {
                Text("A password reset link has been sent to your email.")
            }
        },
        confirmButton = {
            if (!isEmailSent) {
                Button(
                    onClick = {
                        onResetPassword(email)
                    }
                ) {
                    Text("Send")
                }
            } else {
                Button(onClick = onDismiss) {
                    Text("OK")
                }
            }
        }
    )
}

