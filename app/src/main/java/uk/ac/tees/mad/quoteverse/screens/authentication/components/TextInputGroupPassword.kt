package uk.ac.tees.mad.quoteverse.screens.authentication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.quoteverse.R

@Composable
fun TextInputGroupPassword(
    value:String,
    onValueChange:(String)->Unit,
    modifier: Modifier = Modifier) {

    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier=modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Password",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = value,
            onValueChange = {onValueChange(it)},
            label = { Text("Your password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) ImageVector.vectorResource(R.drawable.baseline_visibility_off_24)
                            else ImageVector.vectorResource(R.drawable.baseline_visibility_24)
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}