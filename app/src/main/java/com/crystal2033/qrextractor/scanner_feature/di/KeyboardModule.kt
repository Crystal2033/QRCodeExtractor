package com.crystal2033.qrextractor.scanner_feature.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.scanner_feature.data.remote.api.KeyboardApi
import com.crystal2033.qrextractor.scanner_feature.data.repository.KeyboardRepositoryImpl
import com.crystal2033.qrextractor.scanner_feature.domain.repository.KeyboardRepository
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.concrete_use_case.GetKeyboardFromQRCodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeyboardModule {

    @Provides
    @Singleton
    fun provideGetKeyboardUseCase(repository: KeyboardRepository): GetKeyboardFromQRCodeUseCase {
        return GetKeyboardFromQRCodeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideKeyboardRepository(
        keyboardApi: KeyboardApi,
        @ApplicationContext context: Context
    ): KeyboardRepository {
        return KeyboardRepositoryImpl(keyboardApi, context)
    }

    @Provides
    @Singleton
    fun provideKeyboardApi(okHttpClient: OkHttpClient): KeyboardApi {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(KeyboardApi::class.java)
    }

}