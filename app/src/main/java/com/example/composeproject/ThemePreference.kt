package com.example.composeproject

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object ThemePreference {
    private val Context.dataStore by preferencesDataStore("theme_prefs")
    private val THEME_KEY = booleanPreferencesKey("is_dark_mode")

    suspend fun saveTheme(context: Context, isDarkMode: Boolean) {
        context.dataStore.edit { prefs -> prefs[THEME_KEY] = isDarkMode }
    }

    fun loadTheme(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs -> prefs[THEME_KEY] ?: false }
    }
}
