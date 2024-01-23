package com.crystal2033.qrextractor.auth_feature.di

import android.content.Context
import com.crystal2033.qrextractor.auth_feature.data.remote.api.UserApi
import com.crystal2033.qrextractor.auth_feature.data.repository.UserRepositoryImpl
import com.crystal2033.qrextractor.auth_feature.domain.repository.UserRepository
import com.crystal2033.qrextractor.auth_feature.domain.use_case.LoginUserUseCase
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.core.localdb.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideLoginUserUseCase(repository: UserRepository): LoginUserUseCase {
        return LoginUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi,
        @ApplicationContext context: Context,
        dbRoom: AppDatabase,
    ): UserRepository {
        return UserRepositoryImpl(userApi, context, dbRoom.userDao)
    }


    @Provides
    @Singleton
    fun provideUserApi(okHttpClient: OkHttpClient): UserApi {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UserApi::class.java)
    }

}