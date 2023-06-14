package com.example.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthUser(
    val uid: String,
    val idToken: String,
    val email: String
) : Parcelable