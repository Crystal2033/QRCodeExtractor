package com.crystal2033.qrextractor.scanner_feature.scanner.di

import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.ScannedGroupCreatorRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.InsertScannedGroupInDBUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScannedGroupsModule {
//    @Provides
//    @Singleton
//    fun provideGetScannedGroupsWithObjectsUseCase(scannedObjectsListRepository: ScannedObjectsListRepository): GetScannedGroupsWithObjectsUseCase {
//        return GetScannedGroupsWithObjectsUseCase(scannedObjectsListRepository)
//    }

    @Provides
    @Singleton
    fun provideInsertScannedGroupUseCase(repository: ScannedGroupCreatorRepository): InsertScannedGroupInDBUseCase {
        return InsertScannedGroupInDBUseCase(repository)
    }
}