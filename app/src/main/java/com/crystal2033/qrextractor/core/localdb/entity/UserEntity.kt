package com.crystal2033.qrextractor.core.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crystal2033.qrextractor.core.model.User

@Entity(tableName = "User")
data class UserEntity(
    val login: String,
    val password: String,
    val firstName: String,
    val secondName: String,
    val organizationId: Long,

    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0
) {
    fun toUser(): User {
        return User(
            id = userId,
            firstName = firstName,
            secondName = secondName,
            login = login,
            organizationId = organizationId
        )
    }
}