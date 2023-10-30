package com.crystal2033.qrextractor.core.di

import android.app.Application
import androidx.room.Room
import com.crystal2033.qrextractor.core.localdb.AppDatabase
import com.crystal2033.qrextractor.core.localdb.repository.ScannedObjectsListRepository
import com.crystal2033.qrextractor.core.localdb.repository.ScannedObjectsListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideScannedObjectsListRepository(db: AppDatabase) : ScannedObjectsListRepository {
        return ScannedObjectsListRepositoryImpl(db.userDao)
    }


    @Provides
    @Singleton
    fun provideAppDatabase(app : Application) : AppDatabase{
        return Room.databaseBuilder(app, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()
    }
}