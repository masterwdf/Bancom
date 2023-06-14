package com.example.bancom.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.AuthUser
import com.example.domain.entity.EventResult
import com.example.domain.interactor.AuthUseCase
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
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private var _authUser = MutableStateFlow<LoginUIState>(LoginUIState.Loading)
    val authUser: StateFlow<LoginUIState> = _authUser

    private var _authLogin = MutableStateFlow<LoginUIState>(LoginUIState.Loading)
    val authLogin: StateFlow<LoginUIState> = _authLogin

    fun getAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            authUseCase.getAuth().catch {
                _authUser.value = LoginUIState.Error(it as Exception)
            }.flowOn(Dispatchers.IO).collect {
                when (it) {
                    is EventResult.Success -> {
                        _authUser.value = LoginUIState.SuccessAuth(it.value)
                    }

                    is EventResult.Error -> {
                        _authUser.value = LoginUIState.Error(it.exception)
                    }
                }
            }
        }
    }

    fun saveAuth(authUser: AuthUser) {
        viewModelScope.launch(Dispatchers.IO) {
            authUseCase.saveAuth(authUser).catch {
                _authLogin.value = LoginUIState.Error(it as Exception)
                }.flowOn(Dispatchers.IO).collect {
                    when (it) {
                        is EventResult.Success -> {
                            _authLogin.value = LoginUIState.SuccessAuth(it.value)
                        }

                        is EventResult.Error -> {
                            _authLogin.value = LoginUIState.Error(it.exception)
                        }
                    }
                }
        }
    }
}