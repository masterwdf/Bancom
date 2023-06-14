package com.example.data.mapper

import com.example.data.entity.UserEntity
import com.example.domain.entity.User

fun UserEntity.toUserModel(): User {
    return User(
        id, name, username, email, null, phone, website, null
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id, name, username, email, phone, website
    )
}