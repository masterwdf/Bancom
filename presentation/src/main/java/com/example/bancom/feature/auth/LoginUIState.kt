package com.example.bancom.feature.auth

import com.example.domain.entity.AuthUser
import java.lang.Exception

sealed class LoginUIState {
    object Loading : LoginUIState()
    object NotLoading : LoginUIState()
    data class SuccessAuth(val data: AuthUser) : LoginUIState()
    data class Error(val exception: Exception) : LoginUIState()
}