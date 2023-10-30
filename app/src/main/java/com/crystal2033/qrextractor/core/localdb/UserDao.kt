package com.crystal2033.qrextractor.core.localdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.crystal2033.qrextractor.scanner_feature.data.localdb.entity.User
import com.crystal2033.qrextractor.scanner_feature.data.localdb.entity.UserWithScannedGroupsAndObjects

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM USER WHERE userId = :id")
    suspend fun loadUserById(id: Int): User

    @Transaction
    @Query("SELECT * FROM User WHERE userId = :id")
    fun getUserWithScannedGroupsAndObjects(id: Long): UserWithScannedGroupsAndObjects
}