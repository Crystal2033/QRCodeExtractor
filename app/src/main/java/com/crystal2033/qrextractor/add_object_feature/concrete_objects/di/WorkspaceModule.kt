package com.crystal2033.qrextractor.add_object_feature.concrete_objects.di

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person.DepartmentApi
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person.WorkspaceApi
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person.DepartmentRepositoryImpl
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person.WorkspaceRepositoryImpl
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.DepartmentRepository
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.WorkspaceRepository
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.GetDepartmentsUseCase
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.GetWorkspacesUseCase
import com.crystal2033.qrextractor.core.ApiInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkspaceModule {

    @Provides
    @Singleton
    fun provideWorkspaceUseCase(workspaceRepository: WorkspaceRepository) : GetWorkspacesUseCase {
        return GetWorkspacesUseCase(workspaceRepository)
    }

    @Provides
    @Singleton
    fun providesWorkspaceRepositoryImpl(workspaceApi: WorkspaceApi) : WorkspaceRepository{
        return WorkspaceRepositoryImpl(workspaceApi)
    }

    @Provides
    @Singleton
    fun provideWorkspaceApi(okHttpClient: OkHttpClient): WorkspaceApi {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(WorkspaceApi::class.java)
    }
}