package com.crystal2033.qrextractor.core.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.core.remote_server.api.BuildingAPI
import com.crystal2033.qrextractor.core.remote_server.domain.repository.impl.BuildingRepositoryImpl
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.BuildingRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetBuildingsUseCase
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
object BuildingModule {
    @Provides
    @Singleton
    fun provideGetBuildingsUseCase(buildingRepository: BuildingRepository): GetBuildingsUseCase {
        return GetBuildingsUseCase(buildingRepository)
    }

    @Provides
    @Singleton
    fun provideBuildingRepository(
        buildingAPI: BuildingAPI,
        @ApplicationContext context: Context
    ): BuildingRepository {
        return BuildingRepositoryImpl(buildingAPI, context)
    }

    @Provides
    @Singleton
    fun provideBuildingApi(okHttpClient: OkHttpClient): BuildingAPI {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(BuildingAPI::class.java)
    }
}