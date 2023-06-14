package com.example.domain.repository

import com.example.domain.entity.EventResult
import com.example.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsers(): Flow<EventResult<List<User>>>

    suspend fun getUsersLocal(): Flow<EventResult<List<User>>>

    suspend fun saveUsers(users: List<User>)

    suspend fun deleteUsers()
}