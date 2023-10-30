package com.crystal2033.qrextractor.core.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.crystal2033.qrextractor.scanner_feature.data.localdb.entity.ScannedGroup
import com.crystal2033.qrextractor.scanner_feature.data.localdb.entity.ScannedGroupObjectCrossRef
import com.crystal2033.qrextractor.scanner_feature.data.localdb.entity.ScannedObject
import com.crystal2033.qrextractor.scanner_feature.data.localdb.entity.User

@Database(
    entities = [
        User::class,
        ScannedGroup::class,
        ScannedGroupObjectCrossRef::class,
        ScannedObject::class
    ],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}