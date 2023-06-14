package com.example.bancom.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.EventResult
import com.example.domain.interactor.PostUseCase
import com.example.domain.interactor.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: UserUseCase, private val postUseCase: PostUseCase
) : ViewModel() {

    private var _user = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val user: StateFlow<HomeUIState> = _user

    private var _post = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val post: StateFlow<HomeUIState> = _post

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.getUsers().catch {
                    _user.value = HomeUIState.Error(it as Exception)
                }.flowOn(Dispatchers.IO).collect {
                    when (it) {
                        is EventResult.Success -> {
                            _user.value = HomeUIState.ShowListUser(it.value)
                        }

                        is EventResult.Error -> {
                            _user.value = HomeUIState.Error(it.exception)
                        }
                    }
                }
        }
    }

    fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            postUseCase.getPosts().catch {
                    _post.value = HomeUIState.Error(it as Exception)
                }.flowOn(Dispatchers.IO).collect {
                    when (it) {
                        is EventResult.Success -> {
                            _post.value = HomeUIState.SuccessPost(it.value)
                        }

                        is EventResult.Error -> {
                            _post.value = HomeUIState.Error(it.exception)
                        }
                    }
                }
        }
    }
}