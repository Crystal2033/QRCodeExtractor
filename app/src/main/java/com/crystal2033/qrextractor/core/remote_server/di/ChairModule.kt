package com.crystal2033.qrextractor.core.remote_server.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.core.remote_server.api.ChairAPI
import com.crystal2033.qrextractor.core.remote_server.domain.repository.impl.ChairRepositoryImpl
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ChairRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.AddChairUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.DeleteChairUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.GetAllChairsInCabinetUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.GetChairUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.UpdateChairUseCase
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
object ChairModule {
    @Provides
    @Singleton
    fun provideGetChairsUseCase(chairRepository: ChairRepository): GetAllChairsInCabinetUseCase {
        return GetAllChairsInCabinetUseCase(chairRepository)
    }

    @Provides
    @Singleton
    fun provideGetChairUseCase(chairRepository: ChairRepository): GetChairUseCase {
        return GetChairUseCase(chairRepository)
    }

    @Provides
    @Singleton
    fun provideAddChairUseCase(chairRepository: ChairRepository): AddChairUseCase {
        return AddChairUseCase(chairRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateChairUseCase(chairRepository: ChairRepository): UpdateChairUseCase {
        return UpdateChairUseCase(chairRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteChairUseCase(chairRepository: ChairRepository): DeleteChairUseCase {
        return DeleteChairUseCase(chairRepository)
    }

    @Provides
    @Singleton
    fun provideChairRepository(
        chairAPI: ChairAPI,
        @ApplicationContext context: Context
    ): ChairRepository {
        return ChairRepositoryImpl(chairAPI, context)
    }

    @Provides
    @Singleton
    fun provideChairApi(okHttpClient: OkHttpClient): ChairAPI {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ChairAPI::class.java)
    }
}