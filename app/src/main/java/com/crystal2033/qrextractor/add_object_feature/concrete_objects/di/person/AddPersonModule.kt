package com.crystal2033.qrextractor.add_object_feature.concrete_objects.di.person

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.AddNewPersonUseCase
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.GetDepartmentsUseCase
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.GetTitlesUseCase
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.GetWorkspacesUseCase
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.domain.use_case.person.PersonGetterUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}