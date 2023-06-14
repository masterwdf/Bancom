package com.example.data.repository.datasource.auth

import com.example.data.manager.PreferencesDataStore
import com.example.data.manager.PreferencesDataStore.Companion.USER_EMAIL
import com.example.data.manager.PreferencesDataStore.Companion.USER_LOGIN
import com.example.data.mapper.toAuthUserEntity
import com.example.data.mapper.toAuthUserModel
import com.example.domain.entity.AuthUser
import com.example.domain.entity.EventResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthLocalDataStore(private val preferencesDataStore: PreferencesDataStore) : AuthDataStore {

    override suspend fun saveAuth(authUser: AuthUser): Flow<EventResult<AuthUser>> = flow {
        val entity = authUser.toAuthUserEntity()
        preferencesDataStore.clearAllPreference<Void>()
        preferencesDataStore.putPreference(USER_LOGIN, true)
        preferencesDataStore.putPreference(USER_EMAIL, entity.email)
        emit(EventResult.Success(entity.toAuthUserModel()))
    }

    override suspend fun getAuth(): Flow<EventResult<AuthUser>> = flow {
        preferencesDataStore.getPreference(USER_EMAIL, "").collect {
            emit(EventResult.Success(AuthUser("", "", it)))
        }
    }

    override suspend fun refreshToken(): Flow<EventResult<AuthUser>> {
        TODO("Not yet implemented")
    }
}