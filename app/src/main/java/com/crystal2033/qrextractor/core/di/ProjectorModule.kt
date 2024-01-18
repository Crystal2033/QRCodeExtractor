package com.crystal2033.qrextractor.core.di

import android.content.Context
import com.crystal2033.qrextractor.core.ApiInfo
import com.crystal2033.qrextractor.core.remote_server.api.ProjectorAPI
import com.crystal2033.qrextractor.core.remote_server.domain.repository.impl.ProjectorRepositoryImpl
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ProjectorRepository
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector.AddProjectorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector.DeleteProjectorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector.GetAllProjectorsInCabinetUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector.GetProjectorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector.UpdateProjectorUseCase
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
object ProjectorModule {
    @Provides
    @Singleton
    fun provideGetProjectorsUseCase(projectorRepository: ProjectorRepository): GetAllProjectorsInCabinetUseCase {
        return GetAllProjectorsInCabinetUseCase(projectorRepository)
    }

    @Provides
    @Singleton
    fun provideGetProjectorUseCase(projectorRepository: ProjectorRepository): GetProjectorUseCase {
        return GetProjectorUseCase(projectorRepository)
    }

    @Provides
    @Singleton
    fun provideAddProjectorUseCase(projectorRepository: ProjectorRepository): AddProjectorUseCase {
        return AddProjectorUseCase(projectorRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateProjectorUseCase(projectorRepository: ProjectorRepository): UpdateProjectorUseCase {
        return UpdateProjectorUseCase(projectorRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteProjectorUseCase(projectorRepository: ProjectorRepository): DeleteProjectorUseCase {
        return DeleteProjectorUseCase(projectorRepository)
    }

    @Provides
    @Singleton
    fun provideProjectorRepository(
        projectorAPI: ProjectorAPI,
        @ApplicationContext context: Context
    ): ProjectorRepository {
        return ProjectorRepositoryImpl(projectorAPI, context)
    }

    @Provides
    @Singleton
    fun provideProjectorApi(okHttpClient: OkHttpClient): ProjectorAPI {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ProjectorAPI::class.java)
    }
}