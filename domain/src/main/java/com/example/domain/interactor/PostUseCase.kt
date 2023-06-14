package com.example.domain.interactor

import com.example.domain.entity.EventResult
import com.example.domain.entity.Post
import com.example.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostUseCase @Inject constructor(
    private val repository: PostRepository
) {

    suspend fun getPosts(): Flow<EventResult<List<Post>>> {
        val result = repository.getPosts()

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
            repository.deletePosts()

            result.collect { it ->
                when (it) {
                    is EventResult.Error -> {}
                    is EventResult.Success -> {
                        it.value.let {
                            repository.savePosts(it)
                        }
                    }
                }
            }

            result
        } else {
            repository.getPostsLocal()
        }
    }

    suspend fun getPostsLocal(): Flow<EventResult<List<Post>>> {
        return repository.getPostsLocal()
    }

    suspend fun addPosts(post: Post): Flow<EventResult<Post>> {
        return repository.addPosts(post)
    }

    suspend fun savePosts(posts: List<Post>) {
        return repository.savePosts(posts)
    }

    suspend fun deletePosts() {
        return repository.deletePosts()
    }
}