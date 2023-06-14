package com.example.data.repository

import com.example.data.repository.datasource.auth.AuthDataStoreFactory
import com.example.domain.entity.AuthUser
import com.example.domain.entity.EventResult
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthDataRepository @Inject constructor(
    private val authDataStoreFactory: AuthDataStoreFactory
) : AuthRepository {

    override suspend fun saveAuth(authUser: AuthUser): Flow<EventResult<AuthUser>> {
        val localDataStore = authDataStoreFactory.createLocal()
        return localDataStore.saveAuth(authUser)
    }

    override suspend fun getAuth(): Flow<EventResult<AuthUser>> {
        val localDataStore = authDataStoreFactory.createLocal()
        return localDataStore.getAuth()
    }

    override suspend fun refreshToken(): Flow<EventResult<AuthUser>> {
        val localDataStore = authDataStoreFactory.createLocal()
        return localDataStore.refreshToken()
    }
}