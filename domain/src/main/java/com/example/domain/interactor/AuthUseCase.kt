package com.example.domain.interactor

import com.example.domain.entity.AuthUser
import com.example.domain.entity.EventResult
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend fun saveAuth(authUser: AuthUser) : Flow<EventResult<AuthUser>> {
        return repository.saveAuth(authUser)
    }

    suspend fun getAuth() : Flow<EventResult<AuthUser>> {
        return repository.getAuth()
    }

    suspend fun refreshToken() : Flow<EventResult<AuthUser>> {
        return repository.refreshToken()
    }
}