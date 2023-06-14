package com.example.data.repository.datasource.post

import com.example.domain.entity.Post
import com.example.domain.entity.EventResult
import kotlinx.coroutines.flow.Flow

interface PostDataStore {

    suspend fun getPosts(userId: String): Flow<EventResult<List<Post>>>

    suspend fun addPosts(post: Post): Flow<EventResult<Post>>

    suspend fun savePosts(posts: List<Post>)

    suspend fun deletePosts()
}