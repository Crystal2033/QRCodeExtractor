package com.crystal2033.qrextractor.core.remote_server.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.core.remote_server.api.BranchAPI
import com.crystal2033.qrextractor.core.remote_server.domain.repository.impl.BranchRepositoryImpl
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.BranchRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.branch.GetBranchUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.branch.GetBranchesUseCase
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
object BranchModule {
    @Provides
    @Singleton
    fun provideGetBranchesUseCase(branchRepository: BranchRepository): GetBranchesUseCase {
        return GetBranchesUseCase(branchRepository)
    }

    @Provides
    @Singleton
    fun provideGetBranchUseCase(branchRepository: BranchRepository): GetBranchUseCase {
        return GetBranchUseCase(branchRepository)
    }

    @Provides
    @Singleton
    fun provideBranchRepository(
        branchAPI: BranchAPI,
        @ApplicationContext context: Context
    ): BranchRepository {
        return BranchRepositoryImpl(branchAPI, context)
    }

    @Provides
    @Singleton
    fun provideBranchApi(okHttpClient: OkHttpClient): BranchAPI {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(BranchAPI::class.java)
    }
}