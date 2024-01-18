package com.crystal2033.qrextractor.core.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.core.remote_server.api.CabinetAPI
import com.crystal2033.qrextractor.core.remote_server.domain.repository.impl.CabinetRepositoryImpl
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.CabinetRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetCabinetsUseCase
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
object CabinetModule {
    @Provides
    @Singleton
    fun provideGetCabinetsUseCase(cabinetRepository: CabinetRepository): GetCabinetsUseCase {
        return GetCabinetsUseCase(cabinetRepository)
    }

    @Provides
    @Singleton
    fun provideCabinetRepository(
        cabinetAPI: CabinetAPI,
        @ApplicationContext context: Context
    ): CabinetRepository {
        return CabinetRepositoryImpl(cabinetAPI, context)
    }

    @Provides
    @Singleton
    fun provideCabinetApi(okHttpClient: OkHttpClient): CabinetAPI {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CabinetAPI::class.java)
    }
}