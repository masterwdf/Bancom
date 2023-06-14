package com.example.data.mapper

import com.example.data.entity.PostEntity
import com.example.domain.entity.Post

fun PostEntity.toPostModel(): Post {
    return Post(
        id, userId, title, body
    )
}

fun Post.toPostEntity(): PostEntity {
    return PostEntity(
        id, userId, title, body
    )
}