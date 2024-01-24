package com.crystal2033.qrextractor.core.model

import com.crystal2033.qrextractor.core.localdb.entity.UserEntity

data class User(
    val id: Long = 0L,
    var firstName: String = "",
    var secondName: String = "",
    var login: String = "",
    var organizationId: Long = 0L
) {
    fun toUserEntity(): UserEntity {
        return UserEntity(
            login = login,
            firstName = firstName,
            secondName = secondName,
            organizationId = organizationId
        )
    }
}