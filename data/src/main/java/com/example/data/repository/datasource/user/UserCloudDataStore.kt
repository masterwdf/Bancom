package com.example.data.repository.datasource.user

import com.example.data.mapper.toUserModel
import com.example.data.net.service.UserService
import com.example.data.net.validateResponse
import com.example.domain.entity.EventResult
import com.example.domain.entity.User
import kotlinx.coroutines.flow.flow

class UserCloudDataStore(
    private val service: UserService
) : UserDataStore {

    override suspend fun getUsers() = flow {
        try {
            val result = service.getUsers().validateResponse {
                this.map {
                    it.toUserModel()
                }
            }
            emit(result)
        } catch (ex: Exception) {
            emit(EventResult.Error(ex))
        }
    }

    override suspend fun saveUsers(users: List<User>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUsers() {
        TODO("Not yet implemented")
    }
}