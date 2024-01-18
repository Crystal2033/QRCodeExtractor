package com.crystal2033.qrextractor.core.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.core.remote_server.api.ChairAPI
import com.crystal2033.qrextractor.core.remote_server.api.DeskAPI
import com.crystal2033.qrextractor.core.remote_server.domain.repository.impl.DeskRepositoryImpl
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.DeskRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk.AddDeskUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk.DeleteDeskUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk.GetAllDesksInCabinetUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk.GetDeskUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk.UpdateDeskUseCase
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
object DeskModule {
    @Provides
    @Singleton
    fun provideGetDesksUseCase(deskRepository: DeskRepository): GetAllDesksInCabinetUseCase {
        return GetAllDesksInCabinetUseCase(deskRepository)
    }

    @Provides
    @Singleton
    fun provideGetDeskUseCase(deskRepository: DeskRepository): GetDeskUseCase {
        return GetDeskUseCase(deskRepository)
    }

    @Provides
    @Singleton
    fun provideAddDeskUseCase(deskRepository: DeskRepository): AddDeskUseCase {
        return AddDeskUseCase(deskRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateDeskUseCase(deskRepository: DeskRepository): UpdateDeskUseCase {
        return UpdateDeskUseCase(deskRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteDeskUseCase(deskRepository: DeskRepository): DeleteDeskUseCase {
        return DeleteDeskUseCase(deskRepository)
    }

    @Provides
    @Singleton
    fun provideDeskRepository(
        deskApi: DeskAPI,
        @ApplicationContext context: Context
    ): DeskRepository {
        return DeskRepositoryImpl(deskApi, context)
    }

    @Provides
    @Singleton
    fun provideDeskApi(okHttpClient: OkHttpClient): DeskAPI {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DeskAPI::class.java)
    }
}