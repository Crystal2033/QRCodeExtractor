package com.crystal2033.qrextractor.scanner_feature.general.di

import android.content.Context
import com.crystal2033.qrextractor.core.localdb.AppDatabase
import com.crystal2033.qrextractor.scanner_feature.general.repository_impl.ScannedGroupRepositoryImpl
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository.DeleteObjectItemInScannedGroupRepository
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository.DeleteScannedGroupRepository
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.repository.UserWithScannedGroupsRepository
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.use_case.DeleteObjectItemInScannedGroupUseCase
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.use_case.DeleteScannedGroupUseCase
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.use_case.GetListOfUserScannedGroupsUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.ScannedGroupCreatorRepository
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.InsertScannedGroupInDBUseCase
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
    fun provideScannedGroupRepositoryImplToCreateData(
        db: AppDatabase,
        @ApplicationContext context: Context
    ): ScannedGroupCreatorRepository {
        return ScannedGroupRepositoryImpl(
            db.scannedObjectDao,
            db.scannedGroupDao,
            db.userDao,
            context
        )
    }

    @Provides
    @Singleton
    fun provideGetListOfUserScannedGroupsUseCase(repository: UserWithScannedGroupsRepository): GetListOfUserScannedGroupsUseCase {
        return GetListOfUserScannedGroupsUseCase(repository)
    }

    @Provides //Not singleton because ScannedGroupRepositoryImpl is singleton
    fun provideScannedGroupRepositoryToGetData(
        db: AppDatabase,
        @ApplicationContext context: Context
    ): UserWithScannedGroupsRepository {
        return ScannedGroupRepositoryImpl(
            db.scannedObjectDao,
            db.scannedGroupDao,
            db.userDao,
            context
        )
    }

    @Provides
    @Singleton
    fun provideDeleteObjectInScannedGroupUseCase(repository: DeleteObjectItemInScannedGroupRepository): DeleteObjectItemInScannedGroupUseCase {
        return DeleteObjectItemInScannedGroupUseCase(repository)
    }

    @Provides
    fun provideDeleteObjectInScannedGroupRepository(
        db: AppDatabase,
        @ApplicationContext context: Context
    ): DeleteObjectItemInScannedGroupRepository {
        return ScannedGroupRepositoryImpl(
            db.scannedObjectDao,
            db.scannedGroupDao,
            db.userDao,
            context
        )
    }

    @Provides
    @Singleton
    fun provideDeleteScannedGroupUseCase(repository: DeleteScannedGroupRepository): DeleteScannedGroupUseCase {
        return DeleteScannedGroupUseCase(repository)
    }

    @Provides
    fun provideDeleteScannedGroupRepository(
        db: AppDatabase,
        @ApplicationContext context: Context
    ): DeleteScannedGroupRepository {
        return ScannedGroupRepositoryImpl(
            db.scannedObjectDao,
            db.scannedGroupDao,
            db.userDao,
            context
        )
    }
}