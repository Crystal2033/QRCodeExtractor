package com.crystal2033.qrextractor.core.remote_server.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.core.remote_server.api.OrganizationAPI
import com.crystal2033.qrextractor.core.remote_server.domain.repository.impl.OrganizationRepositoryImpl
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.OrganizationRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetOrganizationsUseCase
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
object OrganizationModule {
    @Provides
    @Singleton
    fun provideGetOrganizationsUseCase(organizationRepository: OrganizationRepository): GetOrganizationsUseCase {
        return GetOrganizationsUseCase(organizationRepository)
    }

    @Provides
    @Singleton
    fun provideOrganizationRepository(
        organizationAPI: OrganizationAPI,
        @ApplicationContext context: Context
    ): OrganizationRepository {
        return OrganizationRepositoryImpl(organizationAPI, context)
    }

    @Provides
    @Singleton
    fun provideOrganizationApi(okHttpClient: OkHttpClient): OrganizationAPI {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(OrganizationAPI::class.java)
    }
}