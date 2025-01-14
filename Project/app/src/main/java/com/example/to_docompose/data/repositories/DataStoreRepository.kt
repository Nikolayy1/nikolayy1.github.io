package com.example.to_docompose.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.util.Constants.PREFERENCE_KEY
import com.example.to_docompose.util.Constants.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object PreferenceKeys {
        val sortKey = stringPreferencesKey(name = PREFERENCE_KEY)
        val xpKey = intPreferencesKey(name = "XP_KEY")
        val levelKey = intPreferencesKey(name = "LEVEL_KEY")
    }

    private val dataStore = context.dataStore

    // Persist Sort State
    suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.sortKey] = priority.name
        }
    }

    // Read Sort State
    val readSortState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val sortState = preferences[PreferenceKeys.sortKey] ?: Priority.NONE.name
            sortState
        }

    // Persist XP and Level
    suspend fun saveXPAndLevel(xp: Int, level: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.xpKey] = xp
            preferences[PreferenceKeys.levelKey] = level
        }
    }

    // Read XP and Level
    val readXPAndLevel: Flow<Pair<Int, Int>> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val xp = preferences[PreferenceKeys.xpKey] ?: 0
            val level = preferences[PreferenceKeys.levelKey] ?: 1
            xp to level
        }
}
