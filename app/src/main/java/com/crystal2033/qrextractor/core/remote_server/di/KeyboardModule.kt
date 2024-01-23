package com.crystal2033.qrextractor.core.remote_server.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.core.remote_server.api.KeyboardAPI
import com.crystal2033.qrextractor.core.remote_server.domain.repository.impl.KeyboardRepositoryImpl
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.KeyboardRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard.AddKeyboardUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard.DeleteKeyboardUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard.GetAllKeyboardsInCabinetUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard.GetKeyboardUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard.UpdateKeyboardUseCase
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
object KeyboardModule {
    @Provides
    @Singleton
    fun provideGetKeyboardsUseCase(keyboardRepository: KeyboardRepository): GetAllKeyboardsInCabinetUseCase {
        return GetAllKeyboardsInCabinetUseCase(keyboardRepository)
    }

    @Provides
    @Singleton
    fun provideGetKeyboardUseCase(keyboardRepository: KeyboardRepository): GetKeyboardUseCase {
        return GetKeyboardUseCase(keyboardRepository)
    }

    @Provides
    @Singleton
    fun provideAddKeyboardUseCase(keyboardRepository: KeyboardRepository): AddKeyboardUseCase {
        return AddKeyboardUseCase(keyboardRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateKeyboardUseCase(keyboardRepository: KeyboardRepository): UpdateKeyboardUseCase {
        return UpdateKeyboardUseCase(keyboardRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteKeyboardUseCase(keyboardRepository: KeyboardRepository): DeleteKeyboardUseCase {
        return DeleteKeyboardUseCase(keyboardRepository)
    }

    @Provides
    @Singleton
    fun provideKeyboardRepository(
        keyboardAPI: KeyboardAPI,
        @ApplicationContext context: Context
    ): KeyboardRepository {
        return KeyboardRepositoryImpl(keyboardAPI, context)
    }

    @Provides
    @Singleton
    fun provideKeyboardApi(okHttpClient: OkHttpClient): KeyboardAPI {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(KeyboardAPI::class.java)
    }
}