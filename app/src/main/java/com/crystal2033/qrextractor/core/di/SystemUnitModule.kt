package com.crystal2033.qrextractor.core.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.core.remote_server.api.SystemUnitAPI
import com.crystal2033.qrextractor.core.remote_server.domain.repository.impl.SystemUnitRepositoryImpl
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.SystemUnitRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit.AddSystemUnitUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit.DeleteSystemUnitUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit.GetAllSystemUnitsInCabinetUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit.GetSystemUnitUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit.UpdateSystemUnitUseCase
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
object SystemUnitModule {
    @Provides
    @Singleton
    fun provideGetSystemUnitsUseCase(systemUnitRepository: SystemUnitRepository): GetAllSystemUnitsInCabinetUseCase {
        return GetAllSystemUnitsInCabinetUseCase(systemUnitRepository)
    }

    @Provides
    @Singleton
    fun provideGetSystemUnitUseCase(systemUnitRepository: SystemUnitRepository): GetSystemUnitUseCase {
        return GetSystemUnitUseCase(systemUnitRepository)
    }

    @Provides
    @Singleton
    fun provideAddSystemUnitUseCase(systemUnitRepository: SystemUnitRepository): AddSystemUnitUseCase {
        return AddSystemUnitUseCase(systemUnitRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateSystemUnitUseCase(systemUnitRepository: SystemUnitRepository): UpdateSystemUnitUseCase {
        return UpdateSystemUnitUseCase(systemUnitRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteSystemUnitUseCase(systemUnitRepository: SystemUnitRepository): DeleteSystemUnitUseCase {
        return DeleteSystemUnitUseCase(systemUnitRepository)
    }

    @Provides
    @Singleton
    fun provideSystemUnitRepository(
        systemUnitAPI: SystemUnitAPI,
        @ApplicationContext context: Context
    ): SystemUnitRepository {
        return SystemUnitRepositoryImpl(systemUnitAPI, context)
    }

    @Provides
    @Singleton
    fun provideSystemUnitApi(okHttpClient: OkHttpClient): SystemUnitAPI {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(SystemUnitAPI::class.java)
    }
}