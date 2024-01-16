package com.crystal2033.qrextractor.auth_feature.data.dto

import com.crystal2033.qrextractor.core.model.User

data class UserDTO(
    val id: Long,
    val login: String,
    val firstName: String,
    val secondName: String,
    val organizationId: Long
) {
    fun toUser(): User {
        return User(
            id = id,
            firstName = firstName,
            secondName = secondName,
            login = login,
            organizationId = organizationId
        )
    }

}
