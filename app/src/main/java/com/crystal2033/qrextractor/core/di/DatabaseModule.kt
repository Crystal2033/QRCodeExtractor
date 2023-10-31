package com.crystal2033.qrextractor.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.crystal2033.qrextractor.core.localdb.AppDatabase
import com.crystal2033.qrextractor.core.localdb.ScannedGroupDao
import com.crystal2033.qrextractor.core.localdb.ScannedObjectDao
import com.crystal2033.qrextractor.core.localdb.UserDao
import com.crystal2033.qrextractor.scanner_feature.data.repository.ScannedGroupRepositoryImpl
import com.crystal2033.qrextractor.scanner_feature.domain.repository.ScannedGroupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

//    @Provides
//    @Singleton
//    fun provideScannedObjectsListRepository(db: AppDatabase) : ScannedObjectsListRepository {
//        return ScannedObjectsListRepositoryImpl(db.userDao)
//    }

    @Provides
    @Singleton
    fun provideScannedGroupRepositoryImpl(
        db: AppDatabase,
        @ApplicationContext context: Context
    ): ScannedGroupRepository {
        return ScannedGroupRepositoryImpl(db.scannedObjectDao, db.scannedGroupDao, db.userDao, context)
    }


    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()
    }
}