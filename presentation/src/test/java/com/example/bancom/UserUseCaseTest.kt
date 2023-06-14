package com.example.bancom

import com.example.bancom.feature.auth.LoginViewModel
import com.example.domain.entity.EventResult
import com.example.domain.entity.User
import com.example.domain.interactor.UserUseCase
import com.example.domain.repository.UserRepository
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
internal class UserUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: UserRepository

    lateinit var useCase: UserUseCase

    lateinit var viewModel: LoginViewModel

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        useCase = UserUseCase(repository)
        //viewModel = LoginViewModel(useCase)
    }

    @Test
    fun `when userUseCase return empty`() = runBlocking {
        //Given
        coEvery {
            repository.getUsers()
        } returns flowOf(EventResult.Success(testUserListEmpty))

        //When
        useCase.getUsers()

        //Then
        coVerify(exactly = 1) {
            repository.getUsersLocal()
        }
    }

    @Test
    fun `when userUseCase return data`() = runBlocking {
        //Given
        coEvery {
            repository.getUsers()
        } returns flowOf(EventResult.Success(testUserList))

        //When
        useCase.getUsers()

        //Then
        coVerify(exactly = 1) {
            repository.deleteUsers()
        }

        coVerify(exactly = 1) {
            repository.saveUsers(testUserList)
        }

        coVerify(exactly = 0) {
            repository.getUsersLocal()
        }
    }

    companion object {
        val testUserListEmpty = listOf<User>()
        val testUserList = listOf(User(1, "Test", "Test", "Test", null, "", "", null))
    }
}