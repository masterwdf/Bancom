package com.example.domain.entity

sealed class EventResult<T> {
    data class Success<T>(val value: T) : EventResult<T>()
    data class Error<T>(val exception: Exception) : EventResult<T>()
}