package com.crystal2033.qrextractor.add_object_feature.concrete_objects.di.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person.TitleApi
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person.WorkspaceApi
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person.TitleRepositoryImpl
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person.WorkspaceRepositoryImpl
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.TitleRepository
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.WorkspaceRepository
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.GetTitlesUseCase
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
object TitleModule {

    @Provides
    @Singleton
    fun provideTitleUseCase(titleRepository: TitleRepository) : GetTitlesUseCase {
        return GetTitlesUseCase(titleRepository)
    }

    @Provides
    @Singleton
    fun providesTitleRepositoryImpl(titleApi: TitleApi) : TitleRepository {
        return TitleRepositoryImpl(titleApi)
    }

    @Provides
    @Singleton
    fun provideTitleApi(okHttpClient: OkHttpClient): TitleApi {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TitleApi::class.java)
    }
}