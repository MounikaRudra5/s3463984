package uk.ac.tees.mad.quoteverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.quoteverse.data.local.ThemePreferenceManager
import uk.ac.tees.mad.quoteverse.ui.theme.QuoteVerseTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var themePreferenceManager: ThemePreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme by themePreferenceManager.themePreference.collectAsState(initial = false)
            QuoteVerseTheme(darkTheme = isDarkTheme) {
                MyAppNavigation()
            }
        }
    }
}