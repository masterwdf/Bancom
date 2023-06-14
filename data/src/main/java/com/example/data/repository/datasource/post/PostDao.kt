package com.example.data.repository.datasource.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPostById(id: Long): Flow<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePosts(posts: List<PostEntity>)

    @Query("DELETE FROM posts")
    fun deletePosts()
}