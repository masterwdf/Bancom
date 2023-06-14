package com.example.data.repository.datasource.post

import com.example.data.net.service.PostService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostDataStoreFactory @Inject constructor(
    private val postDao: PostDao, private val postService: PostService
) {
    /**
     * Metodo que crea la clase que obtendra los datos desde el Servicio
     */

    fun createCloud(): PostDataStore {
        return PostCloudDataStore(postService)
    }

    /**
     * Metodo que crea la clase que obtendra los datos desde la bd local
     */

    fun createDB(): PostDataStore {
        return PostDBDataStore(postDao)
    }
}