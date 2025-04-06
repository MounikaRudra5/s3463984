package uk.ac.tees.mad.quoteverse.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.quoteverse.R

@Composable
fun SortingDropdown(
    sortOption: String,
    onSortChange: (String) -> Unit,
    onReverseList: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val sortOptions = listOf("None","Author Name", "Date Added")

    Box(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clickable { expanded = true },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Sort by: $sortOption",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f))
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon",
                    tint = MaterialTheme.colorScheme.primary
                )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onReverseList) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_swap_vert_24),
                    contentDescription = "Reverse List",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sortOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSortChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

