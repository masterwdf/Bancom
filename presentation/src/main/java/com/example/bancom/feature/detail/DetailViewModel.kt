package com.example.bancom.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.EventResult
import com.example.domain.entity.Post
import com.example.domain.interactor.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val postUseCase: PostUseCase
) : ViewModel() {

    private var _post = MutableStateFlow<DetailUIState>(DetailUIState.Loading)
    val post: StateFlow<DetailUIState> = _post

    fun addPosts(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            postUseCase.addPosts(post).catch {
                _post.value = DetailUIState.Error(it as Exception)
            }.flowOn(Dispatchers.IO).collect {
                when (it) {
                    is EventResult.Success -> {
                        _post.value = DetailUIState.SuccessPost(it.value)
                    }

                    is EventResult.Error -> {
                        _post.value = DetailUIState.Error(it.exception)
                    }
                }
            }
        }
    }
}