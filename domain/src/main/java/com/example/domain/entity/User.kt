package com.example.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val address: Address?,
    val phone: String,
    val website: String,
    val company: Company?
) : Parcelable