package com.example.data.repository.datasource.user

import com.example.data.exception.BaseException
import com.example.data.mapper.toUserEntity
import com.example.data.mapper.toUserModel
import com.example.domain.entity.EventResult
import com.example.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserDBDataStore(
    private val dao: UserDao
) : UserDataStore {

    override suspend fun getUsers(): Flow<EventResult<List<User>>> = flow {
        dao.getUsers().flowOn(Dispatchers.IO).catch {
            emit(EventResult.Error(BaseException.CustomException(it.message.orEmpty())))
        }.collect { it ->
            val model = it.map {
                it.toUserModel()
            }

            emit(EventResult.Success(model))
        }
    }

    override suspend fun saveUsers(users: List<User>) {
        val entity = users.map {
            it.toUserEntity()
        }

        dao.saveUsers(entity)
    }

    override suspend fun deleteUsers() {
        dao.deleteUsers()
    }
}