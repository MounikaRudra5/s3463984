package uk.ac.tees.mad.quoteverse.screens.authentication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.quoteverse.ui.theme.QuoteVerseTheme

@Composable
fun TextInputGroup(
    headingText:String,
    value:String,
    label:String,
    onValueChange:(String)->Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier=modifier.padding(vertical = 8.dp)) {
        Text(
            text = headingText,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = value,
            onValueChange = {onValueChange(it)},
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TextInputGroupPrev() {
    QuoteVerseTheme {
        TextInputGroup(
            "Email",
            "",
            "Your email",
            {}
        )
    }
}