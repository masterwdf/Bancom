package com.example.data.repository

import com.example.data.repository.datasource.user.UserDataStoreFactory
import com.example.domain.entity.EventResult
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val userDataStoreFactory: UserDataStoreFactory
) : UserRepository {

    override suspend fun getUsers(): Flow<EventResult<List<User>>> {
        val cloudDataStore = userDataStoreFactory.createCloud()
        return cloudDataStore.getUsers()
    }

    override suspend fun getUsersLocal(): Flow<EventResult<List<User>>> {
        val dbDataStore = userDataStoreFactory.createDB()
        return dbDataStore.getUsers()
    }

    override suspend fun saveUsers(users: List<User>) {
        val dbDataStore = userDataStoreFactory.createDB()
        return dbDataStore.saveUsers(users)
    }

    override suspend fun deleteUsers() {
        val dbDataStore = userDataStoreFactory.createDB()
        return dbDataStore.deleteUsers()
    }
}