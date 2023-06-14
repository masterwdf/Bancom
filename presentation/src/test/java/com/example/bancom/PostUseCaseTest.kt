package com.example.bancom

import com.example.bancom.feature.home.HomeViewModel
import com.example.domain.entity.EventResult
import com.example.domain.entity.Post
import com.example.domain.interactor.PostUseCase
import com.example.domain.interactor.UserUseCase
import com.example.domain.repository.PostRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class PostUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: PostRepository

    lateinit var userUseCase: UserUseCase
    lateinit var postUseCase: PostUseCase

    lateinit var viewModel: HomeViewModel

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        postUseCase = PostUseCase(repository)
        viewModel = HomeViewModel(userUseCase, postUseCase)
    }

    @Test
    fun `when userUseCase return empty`() = runBlocking {
        //Given
        coEvery {
            repository.getPosts()
        } returns flowOf(EventResult.Success(testPostListEmpty))

        //When
        postUseCase.getPosts()

        //Then
        coVerify(exactly = 1) {
            repository.getPostsLocal()
        }
    }

    @Test
    fun `when userUseCase return data`() = runBlocking {
        //Given
        coEvery {
            repository.getPosts()
        } returns flowOf(EventResult.Success(testPostList))

        //When
        postUseCase.getPosts()

        //Then
        coVerify(exactly = 1) {
            repository.deletePosts()
        }

        coVerify(exactly = 1) {
            repository.savePosts(testPostListEmpty)
        }

        coVerify(exactly = 0) {
            repository.getPostsLocal()
        }
    }

    companion object {
        val testPostListEmpty = listOf<Post>()
        val testPostList = listOf(Post(1, 1, "Test", "Test"))
    }
}