package com.example.domain.repository

import com.example.domain.entity.AuthUser
import com.example.domain.entity.EventResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun saveAuth(authUser: AuthUser): Flow<EventResult<AuthUser>>

    suspend fun getAuth(): Flow<EventResult<AuthUser>>

    suspend fun refreshToken(): Flow<EventResult<AuthUser>>
}