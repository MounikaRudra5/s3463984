package uk.ac.tees.mad.quoteverse.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("theme_preferences")
@Singleton
class ThemePreferenceManager @Inject constructor(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")
    }

    suspend fun saveThemePreference(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDarkMode
        }
    }

    val themePreference: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[DARK_MODE_KEY] ?: false }
        .distinctUntilChanged()
}