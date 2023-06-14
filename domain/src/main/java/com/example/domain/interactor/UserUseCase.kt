package com.example.domain.interactor

import com.example.domain.entity.EventResult
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun getUsers(): Flow<EventResult<List<User>>> {
        val result = repository.getUsers()

        var isEmpty = false

        result.collect {
            when (it) {
                is EventResult.Error -> {}
                is EventResult.Success -> {
                    isEmpty = it.value.isEmpty()
                }
            }
        }

        return if (!isEmpty) {
            repository.deleteUsers()

            result.collect { it ->
                when (it) {
                    is EventResult.Error -> {}
                    is EventResult.Success -> {
                        it.value.let {
                            repository.saveUsers(it)
                        }
                    }
                }
            }

            result
        } else {
            repository.getUsersLocal()
        }
    }

    suspend fun getUsersLocal(): Flow<EventResult<List<User>>> {
        return repository.getUsersLocal()
    }

    suspend fun saveUsers(users: List<User>) {
        return repository.saveUsers(users)
    }

    suspend fun deleteUsers() {
        return repository.deleteUsers()
    }
}