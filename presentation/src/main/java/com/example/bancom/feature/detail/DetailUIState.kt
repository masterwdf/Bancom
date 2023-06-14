package com.example.bancom.feature.detail

import com.example.domain.entity.Post
import java.lang.Exception

sealed class DetailUIState {
    object Loading : DetailUIState()
    object NotLoading : DetailUIState()
    data class SuccessPost(val data: Post) : DetailUIState()
    data class Error(val exception: Exception) : DetailUIState()
}