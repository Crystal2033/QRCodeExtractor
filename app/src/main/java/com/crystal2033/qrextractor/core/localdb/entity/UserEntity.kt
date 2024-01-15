package com.crystal2033.qrextractor.core.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crystal2033.qrextractor.core.model.User

@Entity(tableName = "User")
data class UserEntity(
    val username: String,
    val password: String,
    val firstName: String,
    val secondName: String,
    val organizationId: Long,
    val login: String,


    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0
) {
    fun toUser(): User {
        return User(
            name = username,
            id = userId,
            firstName = firstName,
            secondName = secondName,
            login = login,
            organizationId = organizationId
        )
    }
}