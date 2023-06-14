package com.example.data.repository.datasource.post

import com.example.data.mapper.toPostEntity
import com.example.data.mapper.toPostModel
import com.example.data.net.service.PostService
import com.example.data.net.validateResponse
import com.example.domain.entity.EventResult
import com.example.domain.entity.Post
import kotlinx.coroutines.flow.flow

class PostCloudDataStore(
    private val service: PostService
) : PostDataStore {

    override suspend fun getPosts(userId: String) = flow {
        try {
            val result = service.getPosts(userId).validateResponse {
                this.map {
                    it.toPostModel()
                }
            }
            emit(result)
        } catch (ex: Exception) {
            emit(EventResult.Error(ex))
        }
    }

    override suspend fun addPosts(post: Post) = flow {
        try {
            val result = service.addPosts(post.toPostEntity()).validateResponse {
                this.toPostModel()
            }
            emit(result)
        } catch (ex: Exception) {
            emit(EventResult.Error(ex))
        }
    }

    override suspend fun savePosts(posts: List<Post>) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePosts() {
        TODO("Not yet implemented")
    }
}