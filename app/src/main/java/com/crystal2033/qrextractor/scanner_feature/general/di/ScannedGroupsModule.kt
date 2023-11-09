package com.crystal2033.qrextractor.scanner_feature.general.di

import android.content.Context
import com.crystal2033.qrextractor.core.localdb.AppDatabase
import com.crystal2033.qrextractor.scanner_feature.general.repository_impl.ScannedGroupRepositoryImpl
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.repository.UserWithScannedGroupsRepository
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.use_case.GetListOfUserScannedGroupsUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.ScannedGroupCreatorRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.InsertScannedGroupInDBUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScannedGroupsModule {

    @Provides
    @Singleton
    fun provideInsertScannedGroupUseCase(repository: ScannedGroupCreatorRepository): InsertScannedGroupInDBUseCase {
        return InsertScannedGroupInDBUseCase(repository)
    }

    @Provides //Not singleton because ScannedGroupRepositoryImpl is singleton
    fun provideScannedGroupRepositoryImpl(
        db: AppDatabase,
        @ApplicationContext context: Context
    ): ScannedGroupCreatorRepository {
        return ScannedGroupRepositoryImpl(db.scannedObjectDao, db.scannedGroupDao, db.userDao, context)
    }

    @Provides
    @Singleton
    fun provideGetListOfUserScannedGroupsUseCase(repository: UserWithScannedGroupsRepository) : GetListOfUserScannedGroupsUseCase {
        return GetListOfUserScannedGroupsUseCase(repository)
    }

    @Provides //Not singleton because ScannedGroupRepositoryImpl is singleton
    fun provideScannedGroupRepository2Impl(
        db: AppDatabase,
        @ApplicationContext context: Context
    ): UserWithScannedGroupsRepository {
        return ScannedGroupRepositoryImpl(db.scannedObjectDao, db.scannedGroupDao, db.userDao, context)
    }
}