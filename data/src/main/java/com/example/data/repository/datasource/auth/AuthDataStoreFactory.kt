package com.example.data.repository.datasource.auth

import android.content.Context
import com.example.data.manager.PreferencesDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataStoreFactory @Inject constructor(private val context: Context) {

    /**
     * Metodo que crea la clase que obtendra los datos desde local
     */

    fun createLocal(): AuthDataStore {
        val preferencesDataStore = PreferencesDataStore(context)
        return AuthLocalDataStore(preferencesDataStore)
    }
}