package com.example.domain.repository

import com.example.domain.entity.Post
import com.example.domain.entity.EventResult
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getPosts(): Flow<EventResult<List<Post>>>

    suspend fun getPostsLocal(): Flow<EventResult<List<Post>>>

    suspend fun addPosts(post: Post): Flow<EventResult<Post>>

    suspend fun savePosts(posts: List<Post>)

    suspend fun deletePosts()
}