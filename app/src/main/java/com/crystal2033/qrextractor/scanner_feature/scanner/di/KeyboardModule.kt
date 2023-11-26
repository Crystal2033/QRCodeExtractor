package com.crystal2033.qrextractor.scanner_feature.scanner.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.scanner_feature.scanner.data.remote.api.ScanKeyboardApi
import com.crystal2033.qrextractor.scanner_feature.scanner.data.repository.KeyboardRepositoryImpl
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.KeyboardRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.GetKeyboardUseCase
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
    fun provideGetKeyboardUseCase(repository: KeyboardRepository): GetKeyboardUseCase {
        return GetKeyboardUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideKeyboardRepository(
        keyboardApi: ScanKeyboardApi,
        @ApplicationContext context: Context
    ): KeyboardRepository {
        return KeyboardRepositoryImpl(keyboardApi, context)
    }

    @Provides
    @Singleton
    fun provideKeyboardApi(okHttpClient: OkHttpClient): ScanKeyboardApi {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ScanKeyboardApi::class.java)
    }

}