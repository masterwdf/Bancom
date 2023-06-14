package com.example.bancom.feature.home

import com.example.domain.entity.Post
import com.example.domain.entity.User
import java.lang.Exception

sealed class HomeUIState {
    object Loading : HomeUIState()
    object NotLoading : HomeUIState()
    data class Error(val exception: Exception) : HomeUIState()
    data class ShowListUser(val data: List<User>) : HomeUIState()
    data class SuccessPost(val data: List<Post>) : HomeUIState()
}