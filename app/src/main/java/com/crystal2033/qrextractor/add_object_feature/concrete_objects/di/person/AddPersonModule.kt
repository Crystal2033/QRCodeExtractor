package com.crystal2033.qrextractor.add_object_feature.concrete_objects.di.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person.AddPersonApi
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.remote.api.person.TitleApi
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person.AddPersonRepositoryImpl
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.data.repository.person.TitleRepositoryImpl
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.AddPersonRepository
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.repository.person.TitleRepository
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.AddNewPersonUseCase
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.GetDepartmentsUseCase
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.GetTitlesUseCase
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.GetWorkspacesUseCase
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.PersonGetterUseCases
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
object AddPersonModule {
    @Provides
    @Singleton
    fun providePersonGetterUseCases(
        addNewPersonUseCase: AddNewPersonUseCase,
        getDepartmentsUseCase: GetDepartmentsUseCase,
        getTitlesUseCase: GetTitlesUseCase,
        getWorkspacesUseCase: GetWorkspacesUseCase
    ): PersonGetterUseCases {
        return PersonGetterUseCases(
            addNewPersonUseCase,
            getDepartmentsUseCase,
            getTitlesUseCase,
            getWorkspacesUseCase
        )
    }


    @Provides
    @Singleton
    fun provideAddNewPersonUseCase(addPersonRepository: AddPersonRepository) : AddNewPersonUseCase {
        return AddNewPersonUseCase(addPersonRepository)
    }

    @Provides
    @Singleton
    fun providesAddPersonRepositoryImpl(addPersonApi: AddPersonApi) : AddPersonRepository {
        return AddPersonRepositoryImpl(addPersonApi)
    }

    @Provides
    @Singleton
    fun provideAddPersonApi(okHttpClient: OkHttpClient): AddPersonApi {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AddPersonApi::class.java)
    }

}