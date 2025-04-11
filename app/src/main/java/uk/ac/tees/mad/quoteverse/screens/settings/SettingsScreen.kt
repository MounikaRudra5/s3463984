package uk.ac.tees.mad.quoteverse.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.quoteverse.utils.Constants
import uk.ac.tees.mad.quoteverse.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier) {
    val name by viewModel.name.collectAsState()
    val isEmailSent by viewModel.isEmailSent.collectAsState()
    var showEditNameSheet by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    val isDarkMode by viewModel.isDarkTheme.collectAsState()

    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Text(
            text = "Settings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ProfileSection(name) {
            showEditNameSheet = true
        }
        HorizontalDivider()

        SettingsOption(icon = Icons.Default.Lock, title = "Reset Password") {
            showResetDialog = true
        }

        DarkModeOption(isDarkMode) {
            viewModel.toggleTheme()
        }

        SettingsOption(icon = Icons.Default.Delete, title = "Delete Account") {

        }

        SettingsOption(icon = Icons.AutoMirrored.Filled.ExitToApp, title = "Log Out") {
            viewModel.logOut()
            navController.navigate(Constants.LOGINSCREEN){
                popUpTo(Constants.MAINSCREEN){
                    inclusive = true
                }
            }
        }
    }

    if (showEditNameSheet) {
        EditNameBottomSheet(
            currentName = name,
            onSave = { newName ->
                viewModel.changeUserName(newName)
                showEditNameSheet = false
            },
            onDismiss = { showEditNameSheet = false }
        )
    }

    if (showResetDialog) {
        ResetPasswordDialog(
            isEmailSent = isEmailSent,
            onResetPassword = { email ->
                viewModel.resetPassword(email)
            },
            onDismiss = {
                showResetDialog = false
                viewModel.setIsEmailSent(false)
            }
        )
    }
}