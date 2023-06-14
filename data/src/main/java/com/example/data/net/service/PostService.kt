package com.example.data.net.service

import com.example.data.entity.PostEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {

    @GET(value = "users/{userId}/posts")
    suspend fun getPosts(@Path("userId") userId: String): Response<List<PostEntity>>

    @POST(value = "posts")
    suspend fun addPosts(@Body post: PostEntity): Response<PostEntity>
}