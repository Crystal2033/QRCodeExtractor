package com.crystal2033.qrextractor.core.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.core.remote_server.api.MonitorAPI
import com.crystal2033.qrextractor.core.remote_server.domain.repository.impl.MonitorRepositoryImpl
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.MonitorRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor.AddMonitorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor.DeleteMonitorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor.GetAllMonitorsInCabinetUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor.GetMonitorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor.UpdateMonitorUseCase
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
object MonitorModule {
    @Provides
    @Singleton
    fun provideGetMonitorsUseCase(monitorRepository: MonitorRepository): GetAllMonitorsInCabinetUseCase {
        return GetAllMonitorsInCabinetUseCase(monitorRepository)
    }

    @Provides
    @Singleton
    fun provideGetMonitorUseCase(monitorRepository: MonitorRepository): GetMonitorUseCase {
        return GetMonitorUseCase(monitorRepository)
    }

    @Provides
    @Singleton
    fun provideAddMonitorUseCase(monitorRepository: MonitorRepository): AddMonitorUseCase {
        return AddMonitorUseCase(monitorRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateMonitorUseCase(monitorRepository: MonitorRepository): UpdateMonitorUseCase {
        return UpdateMonitorUseCase(monitorRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteMonitorUseCase(monitorRepository: MonitorRepository): DeleteMonitorUseCase {
        return DeleteMonitorUseCase(monitorRepository)
    }

    @Provides
    @Singleton
    fun provideMonitorRepository(
        monitorAPI: MonitorAPI,
        @ApplicationContext context: Context
    ): MonitorRepository {
        return MonitorRepositoryImpl(monitorAPI, context)
    }

    @Provides
    @Singleton
    fun provideMonitorApi(okHttpClient: OkHttpClient): MonitorAPI {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MonitorAPI::class.java)
    }
}