package com.example.data.mapper

import com.example.data.entity.AuthUserEntity
import com.example.domain.entity.AuthUser

fun AuthUserEntity.toAuthUserModel(): AuthUser {
    return AuthUser(
        uid, idToken, email
    )
}

fun AuthUser.toAuthUserEntity(): AuthUserEntity {
    return AuthUserEntity(
        uid, idToken, email
    )
}