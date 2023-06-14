package com.example.data.repository.datasource.post

import com.example.data.exception.BaseException
import com.example.data.mapper.toPostEntity
import com.example.data.mapper.toPostModel
import com.example.domain.entity.EventResult
import com.example.domain.entity.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostDBDataStore(
    private val dao: PostDao
) : PostDataStore {

    override suspend fun getPosts(userId: String): Flow<EventResult<List<Post>>> = flow {
        dao.getPosts().flowOn(Dispatchers.IO).catch {
            emit(EventResult.Error(BaseException.CustomException(it.message.orEmpty())))
        }.collect { it ->
            val model = it.map {
                it.toPostModel()
            }

            emit(EventResult.Success(model))
        }
    }

    override suspend fun addPosts(post: Post): Flow<EventResult<Post>> {
        return emptyFlow()
    }

    override suspend fun savePosts(posts: List<Post>) {
        val entity = posts.map {
            it.toPostEntity()
        }

        dao.savePosts(entity)
    }

    override suspend fun deletePosts() {
        dao.deletePosts()
    }
}