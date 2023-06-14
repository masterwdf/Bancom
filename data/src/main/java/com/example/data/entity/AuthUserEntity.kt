package com.example.data.entity

import com.google.gson.annotations.SerializedName

data class AuthUserEntity(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("idToken")
    val idToken: String,
    @SerializedName("email")
    val email: String
)