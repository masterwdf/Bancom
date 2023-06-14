package com.example.data.net.service

import com.example.data.entity.UserEntity
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET(value = "users")
    suspend fun getUsers(): Response<List<UserEntity>>
}