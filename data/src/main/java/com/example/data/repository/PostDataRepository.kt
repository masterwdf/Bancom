package com.example.data.repository

import com.example.data.repository.datasource.post.PostDataStoreFactory
import com.example.domain.entity.Post
import com.example.domain.repository.PostRepository
import com.example.domain.entity.EventResult
import com.example.domain.entity.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostDataRepository @Inject constructor(
    private val postDataStoreFactory: PostDataStoreFactory
) : PostRepository {

    override suspend fun getPosts(): Flow<EventResult<List<Post>>> {
        val userId = "1"
        val cloudDataStore = postDataStoreFactory.createCloud()
        return cloudDataStore.getPosts(userId)
    }

    override suspend fun getPostsLocal(): Flow<EventResult<List<Post>>> {
        val userId = "1"
        val dbDataStore = postDataStoreFactory.createDB()
        return dbDataStore.getPosts(userId)
    }

    override suspend fun addPosts(post: Post): Flow<EventResult<Post>> {
        val cloudDataStore = postDataStoreFactory.createCloud()
        return cloudDataStore.addPosts(post)
    }

    override suspend fun savePosts(posts: List<Post>) {
        val dbDataStore = postDataStoreFactory.createDB()
        return dbDataStore.savePosts(posts)
    }

    override suspend fun deletePosts() {
        val dbDataStore = postDataStoreFactory.createDB()
        return dbDataStore.deletePosts()
    }
}