package com.example.bancom.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.bancom.BuildConfig
import com.example.data.BancomDatabase
import com.example.data.net.service.PostService
import com.example.data.net.service.UserService
import com.example.data.repository.AuthDataRepository
import com.example.data.repository.PostDataRepository
import com.example.data.repository.UserDataRepository
import com.example.data.repository.datasource.auth.AuthDataStoreFactory
import com.example.data.repository.datasource.user.UserDao
import com.example.data.repository.datasource.user.UserDataStoreFactory
import com.example.data.repository.datasource.post.PostDao
import com.example.data.repository.datasource.post.PostDataStoreFactory
import com.example.data.util.Constant
import com.example.domain.interactor.AuthUseCase
import com.example.domain.interactor.PostUseCase
import com.example.domain.interactor.UserUseCase
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.PostRepository
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Room

    @Singleton
    @Provides
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app, BancomDatabase::class.java, Constant.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideUserDao(db: BancomDatabase): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun providePostDao(db: BancomDatabase): PostDao {
        return db.postDao()
    }

    //UseCase

    @Singleton
    @Provides
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun providePostUseCase(postRepository: PostRepository): PostUseCase {
        return PostUseCase(postRepository)
    }

    @Singleton
    @Provides
    fun provideAuthUseCase(authRepository: AuthRepository): AuthUseCase {
        return AuthUseCase(authRepository)
    }

    //Repository

    @Singleton
    @Provides
    fun provideUserRepository(userDataStoreFactory: UserDataStoreFactory): UserRepository {
        return UserDataRepository(userDataStoreFactory)
    }

    @Singleton
    @Provides
    fun providePostRepository(postDataStoreFactory: PostDataStoreFactory): PostRepository {
        return PostDataRepository(postDataStoreFactory)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authDataStoreFactory: AuthDataStoreFactory): AuthRepository {
        return AuthDataRepository(authDataStoreFactory)
    }

    //DataStoreFactory

    @Provides
    fun provideUserDataStoreFactory(
        userDao: UserDao, userService: UserService
    ): UserDataStoreFactory {
        return UserDataStoreFactory(userDao, userService)
    }

    @Provides
    fun providePostDataStoreFactory(
        postDao: PostDao, postService: PostService
    ): PostDataStoreFactory {
        return PostDataStoreFactory(postDao, postService)
    }

    @Provides
    fun provideAuthDataStoreFactory(context: Application): AuthDataStoreFactory {
        return AuthDataStoreFactory(context.applicationContext)
    }

    //Service

    @Provides
    fun provideUserService(@Named("baseUrl") retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun providePostService(@Named("baseUrl") retrofit: Retrofit): PostService {
        return retrofit.create(PostService::class.java)
    }

    //Retrofit

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return BuildConfig.API_BASE
    }

    @Singleton
    @Provides
    @Named("baseUrl")
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
            .baseUrl(baseUrl).build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        return okHttpClientBuilder.build()
    }
}