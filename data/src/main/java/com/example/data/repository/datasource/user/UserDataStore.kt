package com.example.data.repository.datasource.user

import com.example.domain.entity.User
import com.example.domain.entity.EventResult
import kotlinx.coroutines.flow.Flow

interface UserDataStore {

    suspend fun getUsers(): Flow<EventResult<List<User>>>

    suspend fun saveUsers(users: List<User>)

    suspend fun deleteUsers()
}