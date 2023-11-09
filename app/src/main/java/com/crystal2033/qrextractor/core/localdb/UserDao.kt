package com.crystal2033.qrextractor.core.localdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.crystal2033.qrextractor.core.localdb.entity.UserEntity
import com.crystal2033.qrextractor.scanner_feature.scanner.data.localdb.entity.UserWithScannedGroupsAndObjectsRel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM USER WHERE userId = :id")
    suspend fun getUserById(id: Long): UserEntity

    @Transaction
    @Query("SELECT * FROM USER WHERE userId = :id")
    suspend fun getUserWithScannedGroupsAndObjects(id: Long): UserWithScannedGroupsAndObjectsRel
}