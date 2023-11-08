package com.crystal2033.qrextractor.core.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupEntity
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedGroupObjectCrossRef
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.ScannedObjectEntity
import com.crystal2033.qrextractor.core.localdb.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ScannedGroupEntity::class,
        ScannedGroupObjectCrossRef::class,
        ScannedObjectEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val scannedObjectDao: ScannedObjectDao
    abstract val scannedGroupDao: ScannedGroupDao
    abstract val userDao: UserDao
}