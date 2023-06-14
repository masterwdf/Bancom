package com.example.data.repository.datasource.user

import com.example.data.net.service.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataStoreFactory @Inject constructor(
    private val userDao: UserDao, private val userService: UserService
) {
    /**
     * Metodo que crea la clase que obtendra los datos desde el Servicio
     */

    fun createCloud(): UserDataStore {
        return UserCloudDataStore(userService)
    }

    /**
     * Metodo que crea la clase que obtendra los datos desde la bd local
     */

    fun createDB(): UserDataStore {
        return UserDBDataStore(userDao)
    }
}