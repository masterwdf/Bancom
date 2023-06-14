package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.entity.PostEntity
import com.example.data.entity.UserEntity
import com.example.data.repository.datasource.user.UserDao
import com.example.data.repository.datasource.post.PostDao

@Database(
    entities = [UserEntity::class, PostEntity::class], version = 1, exportSchema = false
)

abstract class BancomDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun postDao() : PostDao
}